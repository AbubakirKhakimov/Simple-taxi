package uz.abubakir_khakimov.simple_taxi.core.presentation.extensions

fun Int.mapRange(fromRangeStart: Int, fromRangeEnd: Int, toRangeStart: Int, toRangeEnd: Int): Int =
    (this - fromRangeStart) * (toRangeEnd - toRangeStart) / (fromRangeEnd - fromRangeStart) + toRangeStart

fun Float.mapRange(fromRangeStart: Float, fromRangeEnd: Float, toRangeStart: Float, toRangeEnd: Float): Float =
    (this - fromRangeStart) * (toRangeEnd - toRangeStart) / (fromRangeEnd - fromRangeStart) + toRangeStart