package uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations_table")
data class LocationLocalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val latitude: Double,
    val longitude: Double,
    val bearing: Float,
    val time: Long
)