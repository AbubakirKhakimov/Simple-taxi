package uz.abubakir_khakimov.simple_taxi.domain.locations.usecase

import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location
import uz.abubakir_khakimov.simple_taxi.domain.locations.repositories.LocationsRepository
import javax.inject.Inject

class AddLocationsUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {

    suspend fun invoke(locations: List<Location>) =
        locationsRepository.addLocations(locations = locations)
}