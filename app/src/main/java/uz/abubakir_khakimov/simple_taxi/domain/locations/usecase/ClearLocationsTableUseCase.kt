package uz.abubakir_khakimov.simple_taxi.domain.locations.usecase

import uz.abubakir_khakimov.simple_taxi.domain.locations.repositories.LocationsRepository
import javax.inject.Inject

class ClearLocationsTableUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {

    suspend fun invoke() = locationsRepository.clearLocationsTable()
}