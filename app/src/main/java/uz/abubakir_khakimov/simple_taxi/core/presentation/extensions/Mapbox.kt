package uz.abubakir_khakimov.simple_taxi.core.presentation.extensions

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.viewport.CompletionListener

private const val EASY_TO_CAMERA_MOVE_MAX_DISTANCE = 5000 //meter. 5km

fun Point.calculateDistance(point: Point): Long = calculateDistance(
    fromLat = latitude(), fromLng = longitude(),
    toLat = point.latitude(), toLng = point.longitude()
)

@OptIn(MapboxExperimental::class)
fun MapViewportState.autoTo(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions? = null,
    completionListener: CompletionListener? = null
){
    val distance = if (cameraState == null || cameraOptions.center == null) 0
    else cameraState!!.center.calculateDistance(point = cameraOptions.center!!)

    if (distance <= EASY_TO_CAMERA_MOVE_MAX_DISTANCE) easeTo(
        cameraOptions = cameraOptions,
        animationOptions = animationOptions,
        completionListener = completionListener
    ) else flyTo(
        cameraOptions = cameraOptions,
        animationOptions = animationOptions,
        completionListener = completionListener
    )
}