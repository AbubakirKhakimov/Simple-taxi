package uz.abubakir_khakimov.simple_taxi.provider.local.database.initializer

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.dao.LocationsDao
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.entities.LocationLocalEntity

@Database(
    entities = [
        LocationLocalEntity::class,
    ],
    version = 1
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun locationsDao(): LocationsDao
}