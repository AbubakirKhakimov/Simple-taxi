package uz.abubakir_khakimov.simple_taxi.domain.locations.models

data class Location(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val time: Long
)