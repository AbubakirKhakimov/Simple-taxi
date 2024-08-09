package uz.abubakir_khakimov.simple_taxi.provider.data.features.locations.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.abubakir_khakimov.simple_taxi.core.common.EntityMapper
import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location
import uz.abubakir_khakimov.simple_taxi.domain.locations.repositories.LocationsRepository
import uz.abubakir_khakimov.simple_taxi.core.common.Result
import uz.abubakir_khakimov.simple_taxi.core.common.Result.Companion.map
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.LocationsLocalDataSource
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.entities.LocationLocalEntity
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val locationsLocalDataSource: LocationsLocalDataSource,
    private val locationLocalMapper: EntityMapper<LocationLocalEntity, Location>,
    private val locationMapper: EntityMapper<Location, LocationLocalEntity>,
): LocationsRepository {

    override fun observeLocation(): Result<Flow<Location?>> =
        locationsLocalDataSource.observeLocation().map { locationsFlow ->
            locationsFlow.map { location ->
                location?.let { locationLocalMapper.mapTo(entity = location) }
            }
        }

    override suspend fun addLocation(location: Location) =
        locationsLocalDataSource.addLocation(location = locationMapper.mapTo(entity = location))

    override suspend fun addLocations(locations: List<Location>) =
        locationsLocalDataSource.addLocations(
            locations = locationMapper.mapToList(entityList = locations)
        )

    override suspend fun clearLocationsTable() =
        locationsLocalDataSource.clearLocationsTable()
}