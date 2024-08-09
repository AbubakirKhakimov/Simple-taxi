package uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations

import kotlinx.coroutines.flow.Flow
import uz.abubakir_khakimov.simple_taxi.core.common.Result
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.entities.LocationLocalEntity

interface LocationsLocalDataSource {

    fun observeLocation(): Result<Flow<LocationLocalEntity?>>

    suspend fun addLocation(location: LocationLocalEntity)

    suspend fun addLocations(locations: List<LocationLocalEntity>)

    suspend fun clearLocationsTable()
}