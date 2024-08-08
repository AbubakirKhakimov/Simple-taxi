package uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.sources

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import uz.abubakir_khakimov.simple_taxi.core.common.Result
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.LocationsLocalDataSource
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.dao.LocationsDao
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.entities.LocationLocalEntity
import javax.inject.Inject

class LocationsLocalDataSourceImpl @Inject constructor(
    private val locationsDao: LocationsDao
) : LocationsLocalDataSource {

    override fun observeLocation(): Result<Flow<LocationLocalEntity>> =
        try {
            val data = locationsDao.observeLocation()
            Result.success(data)
        } catch (t: Throwable) {
            Result.error(t)
        }

    override suspend fun getLastLocation(): Result<LocationLocalEntity> =
        try {
            val data = locationsDao.getLastLocation()
            Result.success(data)
        } catch (t: Throwable) {
            Result.error(t)
        }

    override suspend fun addLocation(location: LocationLocalEntity) =
        locationsDao.addLocation(location = location)

    override suspend fun addLocations(locations: List<LocationLocalEntity>) =
        locationsDao.addLocations(locations = locations)

    override suspend fun clearLocationsTable() =
        locationsDao.clearLocationsTable()
}