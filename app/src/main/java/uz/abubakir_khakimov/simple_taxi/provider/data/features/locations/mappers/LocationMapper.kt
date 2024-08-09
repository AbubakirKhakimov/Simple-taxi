package uz.abubakir_khakimov.simple_taxi.provider.data.features.locations.mappers

import uz.abubakir_khakimov.simple_taxi.core.common.EntityMapper
import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location
import uz.abubakir_khakimov.simple_taxi.core.common.Result
import uz.abubakir_khakimov.simple_taxi.core.common.Result.Companion.map
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.entities.LocationLocalEntity

internal class LocationMapper: EntityMapper<Location, LocationLocalEntity> {

    override fun mapTo(entity: Location): LocationLocalEntity =
        LocationLocalEntity(
            id = entity.id,
            latitude = entity.latitude,
            longitude = entity.longitude,
            bearing = entity.bearing,
            time = entity.time
        )

    override fun mapToList(entityList: List<Location>): List<LocationLocalEntity> =
        entityList.map { mapTo(entity = it) }

    override fun mapToResult(entityResult: Result<Location>): Result<LocationLocalEntity> =
        entityResult.map { mapTo(entity = it) }

    override fun mapToResultList(
        entityResultList: Result<List<Location>>
    ): Result<List<LocationLocalEntity>> = entityResultList.map { mapToList(entityList = it) }
}