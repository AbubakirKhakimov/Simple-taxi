package uz.abubakir_khakimov.simple_taxi.domain.locations.repositories

import kotlinx.coroutines.flow.Flow
import uz.abubakir_khakimov.simple_taxi.core.common.Result
import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location

interface LocationsRepository {

    fun observeLocation(): Result<Flow<Location?>>

    suspend fun addLocation(location: Location)

    suspend fun addLocations(locations: List<Location>)

    suspend fun clearLocationsTable()
}