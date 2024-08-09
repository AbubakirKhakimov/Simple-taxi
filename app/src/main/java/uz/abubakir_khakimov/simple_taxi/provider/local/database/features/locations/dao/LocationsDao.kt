package uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.entities.LocationLocalEntity

@Dao
interface LocationsDao {

    @Query("SELECT * FROM locations_table WHERE time = " +
            "(SELECT MAX(time) FROM locations_table)")
    fun observeLocation(): Flow<LocationLocalEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(location: LocationLocalEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocations(locations: List<LocationLocalEntity>)

    @Query("DELETE FROM locations_table")
    suspend fun clearLocationsTable()
}