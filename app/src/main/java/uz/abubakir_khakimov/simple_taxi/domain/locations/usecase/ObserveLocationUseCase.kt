package uz.abubakir_khakimov.simple_taxi.domain.locations.usecase

import kotlinx.coroutines.flow.Flow
import uz.abubakir_khakimov.simple_taxi.core.common.Result
import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location
import uz.abubakir_khakimov.simple_taxi.domain.locations.repositories.LocationsRepository
import javax.inject.Inject

class ObserveLocationUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {

    fun invoke(): Result<Flow<Location?>> = locationsRepository.observeLocation()
}