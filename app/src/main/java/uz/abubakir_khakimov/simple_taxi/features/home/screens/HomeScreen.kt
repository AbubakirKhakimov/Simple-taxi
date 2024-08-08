package uz.abubakir_khakimov.simple_taxi.features.home.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMoveListener
import uz.abubakir_khakimov.simple_taxi.R
import uz.abubakir_khakimov.simple_taxi.core.presentation.extensions.bitmapFromDrawableRes
import uz.abubakir_khakimov.simple_taxi.core.presentation.extensions.mapRange
import uz.abubakir_khakimov.simple_taxi.features.home.viewmodels.HomeViewModel

enum class ZoomLevel(val level: Double) { Max(level = 22.0), Min(level = 0.0) }

@OptIn(MapboxExperimental::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val homeState by viewModel.homeState.collectAsState()
    var zoom by remember { mutableDoubleStateOf(value = 16.0) }
    var autoCameraMove by remember { mutableStateOf(value = true) }
    val taxiIconBitmap by remember {
        mutableStateOf(value = bitmapFromDrawableRes(context, resourceId = R.drawable.ic_car)!!)
    }
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(/* zoom = */ zoom)
            center(
                /* center = */ Point.fromLngLat(
                    /* longitude = */ homeState.location.longitude,
                    /* latitude = */ homeState.location.latitude
                )
            )
            pitch(/* pitch = */ 0.0)
            bearing(/* bearing = */ 0.0)
        }
    }

    if (autoCameraMove) LaunchedEffect(key1 = homeState.location) {
        mapViewportState.flyTo(
            cameraOptions = cameraOptions {
                center(
                    /* center = */ Point.fromLngLat(
                        /* longitude = */ homeState.location.longitude,
                        /* latitude = */ homeState.location.latitude
                    )
                )
                zoom(/* zoom = */ 16.0)
            },
            animationOptions = MapAnimationOptions.mapAnimationOptions { duration(duration = 800) }
        )
    }

    LaunchedEffect(key1 = zoom) {
        mapViewportState.flyTo(
            cameraOptions = cameraOptions { zoom(/* zoom = */ zoom) },
            animationOptions = MapAnimationOptions.mapAnimationOptions { duration(duration = 400) }
        )
    }

    Box {
        MapboxMap(
            modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            compass = {},
            scaleBar = {},
            style = { MapStyle(style = if (isSystemInDarkTheme()) Style.DARK else Style.LIGHT) }
        ){
            PointAnnotationGroup(
                annotations = listOf(
                    PointAnnotationOptions()
                    .withPoint(
                        Point.fromLngLat(
                            /* longitude = */ homeState.location.longitude,
                            /* latitude = */ homeState.location.latitude
                        )
                    )
                    .withIconImage(iconImageBitmap = taxiIconBitmap)
                )
            )

            MapEffect(key1 = Unit) { mapView ->
                val moveListener = object : OnMoveListener {
                    override fun onMove(detector: MoveGestureDetector): Boolean = false
                    override fun onMoveEnd(detector: MoveGestureDetector) = Unit
                    override fun onMoveBegin(detector: MoveGestureDetector) { autoCameraMove = false }
                }
                mapView.mapboxMap.addOnMoveListener(listener = moveListener)
            }
        }

        HighLevelItems(
            modifier,
            camZoomPlusOnClick = {
                zoom += 1.0.coerceIn(
                    minimumValue = ZoomLevel.Min.level,
                    maximumValue = ZoomLevel.Max.level
                )
                autoCameraMove = false
            },
            camZoomMinusOnClick = {
                zoom -= 1.0.coerceIn(
                    minimumValue = ZoomLevel.Min.level,
                    maximumValue = ZoomLevel.Max.level
                )
                autoCameraMove = false
            },
            currentLocationOnClick = {
                zoom = 16.0
                autoCameraMove = true
            }
        )
    }
}

@Composable
fun HighLevelItems(
    modifier: Modifier = Modifier,
    camZoomPlusOnClick: () -> Unit = {},
    camZoomMinusOnClick: () -> Unit = {},
    currentLocationOnClick: () -> Unit = {}
) {
    val bottomSheetState = remember {
        mutableStateOf<BottomSheetState>(value = BottomSheetState.PartlyExpanded)
    }
    var mapControllersState by remember {
        mutableStateOf<MapControllersState>(value = MapControllersState.Expanded)
    }
    var bottomSheetExpanderState by remember {
        mutableStateOf<BottomSheetExpanderState>(value = BottomSheetExpanderState.Expanded)
    }

    LaunchedEffect(key1 = bottomSheetState.value) {
        when (val state = bottomSheetState.value) {
            is BottomSheetState.Expanded -> {
                mapControllersState = MapControllersState.Collapsed
                bottomSheetExpanderState = BottomSheetExpanderState.Collapsed
            }
            is BottomSheetState.PartlyExpanded -> {
                mapControllersState = MapControllersState.Expanded
                bottomSheetExpanderState = BottomSheetExpanderState.Expanded
            }
            is BottomSheetState.Dragging -> {
                mapControllersState = state.position.mapRange(
                    fromRangeStart = BottomSheetState.Expanded.position,
                    fromRangeEnd = BottomSheetState.PartlyExpanded.position,
                    toRangeStart = MapControllersState.Collapsed.position,
                    toRangeEnd = MapControllersState.Expanded.position
                ).toMapControllersDragging()
                bottomSheetExpanderState = state.position.mapRange(
                    fromRangeStart = BottomSheetState.Expanded.position,
                    fromRangeEnd = BottomSheetState.PartlyExpanded.position,
                    toRangeStart = BottomSheetExpanderState.Collapsed.position,
                    toRangeEnd = BottomSheetExpanderState.Expanded.position
                ).toBottomSheetExpanderDragging()
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        ActionBar(modifier = Modifier.align(alignment = Alignment.TopCenter))

        MapControllers(
            modifier = Modifier.align(alignment = Alignment.CenterEnd),
            mapControllersState = mapControllersState,
            camZoomPlusOnClick = camZoomPlusOnClick,
            camZoomMinusOnClick = camZoomMinusOnClick,
            currentLocationOnClick = currentLocationOnClick
        )

        BottomSheetExpander(
            modifier = Modifier.align(alignment = Alignment.CenterStart),
            bottomSheetExpanderState = bottomSheetExpanderState,
            onClick = { bottomSheetState.value = BottomSheetState.Expanded }
        )

        BottomSheet(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            bottomSheetState = bottomSheetState
        )
    }
}