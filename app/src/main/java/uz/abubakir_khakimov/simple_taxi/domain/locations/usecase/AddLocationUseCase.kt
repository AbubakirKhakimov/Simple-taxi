package uz.abubakir_khakimov.simple_taxi.domain.locations.usecase

import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location
import uz.abubakir_khakimov.simple_taxi.domain.locations.repositories.LocationsRepository
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {

    suspend fun invoke(location: Location) = locationsRepository.addLocation(location = location)
}