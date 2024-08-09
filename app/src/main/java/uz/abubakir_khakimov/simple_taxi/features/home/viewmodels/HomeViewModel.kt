package uz.abubakir_khakimov.simple_taxi.features.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.abubakir_khakimov.simple_taxi.core.common.Result
import uz.abubakir_khakimov.simple_taxi.core.common.Result.Companion.asyncSuccess
import uz.abubakir_khakimov.simple_taxi.core.common.Result.Companion.error
import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location
import uz.abubakir_khakimov.simple_taxi.domain.locations.usecase.ObserveLocationUseCase
import uz.abubakir_khakimov.simple_taxi.features.home.models.HomeEvent
import uz.abubakir_khakimov.simple_taxi.features.home.models.HomeState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeLocationUseCase: ObserveLocationUseCase
): ViewModel() {

    private val _homeState = MutableStateFlow(value = getInitialHomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    private var isLocationObserved: Boolean = false

    init {

        sendEvent(event = HomeEvent.ObserveLocation)
    }

    fun sendEvent(event: HomeEvent) = when(event) {
        is HomeEvent.ObserveLocation -> if (!isLocationObserved) observeLocation() else Unit
    }

    private fun observeLocation() {
        isLocationObserved = true
        observeLocationUseCase.invoke().analiseObserveLocation()
    }

    private fun Result<Flow<Location?>>.analiseObserveLocation() = viewModelScope.launch{
        asyncSuccess { data, _ ->
            data?.collect { location ->
                if (location != null) emitHomeState(homeState = HomeState(location = location))
            }
        }
        error { error, _ ->
            isLocationObserved = false
            error?.printStackTrace()
        }
    }

    private fun emitHomeState(homeState: HomeState) = viewModelScope.launch {
        _homeState.emit(value = homeState)
    }

    private fun getInitialHomeState(): HomeState = HomeState(
        location = Location(id = 0, latitude = 0.0, longitude = 0.0, bearing = 0f, time = 0)
    )
}