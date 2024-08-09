package uz.abubakir_khakimov.simple_taxi.features.home.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMoveListener
import com.mapbox.maps.plugin.viewport.CompletionListener
import uz.abubakir_khakimov.simple_taxi.R
import uz.abubakir_khakimov.simple_taxi.core.presentation.extensions.autoTo
import uz.abubakir_khakimov.simple_taxi.core.presentation.extensions.bitmapFromDrawableRes
import uz.abubakir_khakimov.simple_taxi.core.presentation.extensions.mapRange
import uz.abubakir_khakimov.simple_taxi.features.home.viewmodels.HomeViewModel

enum class ZoomLevel(val level: Double) { Max(level = 22.0), Min(level = 0.0) }

const val MAPBOX_LIGHT_STYLE_URL = "mapbox://styles/khakimov-dev/clzmuixqk006s01qxbesk81sa"
const val MAPBOX_NIGHT_STYLE_URL = "mapbox://styles/khakimov-dev/clzmuvja1006j01pf2t1t6fki"

const val AUTO_CAMERA_MOVE_DEF_ZOOM = 16.0

@OptIn(MapboxExperimental::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val homeState by viewModel.homeState.collectAsState()
    var autoCameraMove by remember { mutableStateOf(value = true) }
    val taxiIconBitmap by remember {
        mutableStateOf(value = bitmapFromDrawableRes(context, resourceId = R.drawable.ic_car)!!)
    }
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(/* zoom = */ AUTO_CAMERA_MOVE_DEF_ZOOM)
            center(
                /* center = */ Point.fromLngLat(
                    /* longitude = */ homeState.location.longitude,
                    /* latitude = */ homeState.location.latitude
                )
            )
        }
    }

    if (autoCameraMove) LaunchedEffect(key1 = homeState.location) {
        mapViewportState.changeCenterPointWithAnimation(
            latitude = homeState.location.latitude,
            longitude = homeState.location.longitude
        )
    }

    Box {
        MapboxMap(
            modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            compass = {},
            scaleBar = {},
            style = {
                MapStyle(
                    style = if (isSystemInDarkTheme()) MAPBOX_NIGHT_STYLE_URL
                    else MAPBOX_LIGHT_STYLE_URL
                )
            }
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
                    .withIconRotate(iconRotate = homeState.location.bearing.toDouble())
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
            modifier = modifier,
            camZoomPlusOnClick = {
                mapViewportState.changeZoomWithAnimation(
                    modifyZoom = { oldZoom -> oldZoom + 1.0 },
                    completionListener = { autoCameraMove = false }
                )
            },
            camZoomMinusOnClick = {
                mapViewportState.changeZoomWithAnimation(
                    modifyZoom = { oldZoom -> oldZoom - 1.0 },
                    completionListener = { autoCameraMove = false }
                )
            },
            currentLocationOnClick = { autoCameraMove = true }
        )
    }
}

@OptIn(MapboxExperimental::class)
fun MapViewportState.changeZoomWithAnimation(
    modifyZoom: (oldZoom: Double) -> Double,
    completionListener: CompletionListener? = null
) = easeTo(
    cameraOptions = cameraOptions {
        zoom(/* zoom = */ modifyZoom(cameraState?.zoom ?: AUTO_CAMERA_MOVE_DEF_ZOOM)
            .coerceIn(minimumValue = ZoomLevel.Min.level, maximumValue = ZoomLevel.Max.level)
        )
    },
    animationOptions = MapAnimationOptions.mapAnimationOptions { duration(duration = 400) },
    completionListener = completionListener
)

@OptIn(MapboxExperimental::class)
fun MapViewportState.changeCenterPointWithAnimation(
    latitude: Double,
    longitude: Double,
    zoom: Double = AUTO_CAMERA_MOVE_DEF_ZOOM,
    completionListener: CompletionListener? = null
) = autoTo(
    cameraOptions = cameraOptions {
        center(
            /* center = */ Point.fromLngLat(/* longitude = */ longitude, /* latitude = */ latitude)
        )
        zoom(/* zoom = */ zoom)
    },
    animationOptions = MapAnimationOptions.mapAnimationOptions { duration(duration = 800) },
    completionListener = completionListener
)

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