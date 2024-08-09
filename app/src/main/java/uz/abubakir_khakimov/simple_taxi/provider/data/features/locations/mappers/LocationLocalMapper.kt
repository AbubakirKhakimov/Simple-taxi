package uz.abubakir_khakimov.simple_taxi.provider.data.features.locations.mappers

import uz.abubakir_khakimov.simple_taxi.core.common.EntityMapper
import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location
import uz.abubakir_khakimov.simple_taxi.core.common.Result
import uz.abubakir_khakimov.simple_taxi.core.common.Result.Companion.map
import uz.abubakir_khakimov.simple_taxi.provider.local.database.features.locations.entities.LocationLocalEntity

internal class LocationLocalMapper: EntityMapper<LocationLocalEntity, Location> {

    override fun mapTo(entity: LocationLocalEntity): Location =
        Location(
            id = entity.id,
            latitude = entity.latitude,
            longitude = entity.longitude,
            bearing = entity.bearing,
            time = entity.time
        )

    override fun mapToList(entityList: List<LocationLocalEntity>): List<Location> =
        entityList.map { mapTo(entity = it) }

    override fun mapToResult(entityResult: Result<LocationLocalEntity>): Result<Location> =
        entityResult.map { mapTo(entity = it) }

    override fun mapToResultList(
        entityResultList: Result<List<LocationLocalEntity>>
    ): Result<List<Location>> = entityResultList.map { mapToList(entityList = it) }
}