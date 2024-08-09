package uz.abubakir_khakimov.simple_taxi.core.presentation.extensions

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun calculateDistance(fromLat: Double, fromLng: Double, toLat: Double, toLng: Double): Long {
    val dLat = Math.toRadians(/* angdeg = */ toLat - fromLat)
    val dLon = Math.toRadians(/* angdeg = */ toLng - fromLng)
    val a = (sin(x = dLat / 2) * sin(x = dLat / 2)
            + (cos(x = Math.toRadians(/* angdeg = */ fromLat))
            * cos(x = Math.toRadians(/* angdeg = */ toLat)) * sin(x = dLon / 2)
            * sin(x = dLon / 2)))
    val c = 2 * asin(x = sqrt(x = a))
    return Math.round(/* a = */ 6371000 * c)
}