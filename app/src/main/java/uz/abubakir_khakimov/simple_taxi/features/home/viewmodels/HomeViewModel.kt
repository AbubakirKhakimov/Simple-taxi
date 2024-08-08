package uz.abubakir_khakimov.simple_taxi.features.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location
import uz.abubakir_khakimov.simple_taxi.core.common.Result
import uz.abubakir_khakimov.simple_taxi.core.common.Result.Companion.asyncSuccess
import uz.abubakir_khakimov.simple_taxi.core.common.Result.Companion.error
import uz.abubakir_khakimov.simple_taxi.domain.locations.usecase.GetLastLocationUseCase
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

    private var observeLocationScope: CoroutineScope? = null
    private var isLocationObserved: Boolean = false

    init {

        sendEvent(event = HomeEvent.ObserveLocation)
    }

    fun sendEvent(event: HomeEvent) = when(event) {
        is HomeEvent.ObserveLocation -> if (!isLocationObserved) observeLocation() else Unit
        is HomeEvent.StopObserveLocation -> if (isLocationObserved) stopObserveLocation() else Unit
    }

    private fun observeLocation() {
        observeLocationScope = CoroutineScope(context = SupervisorJob() + Dispatchers.IO)
        isLocationObserved = true

        observeLocationUseCase.invoke().analiseObserveLocation()
    }

    private fun Result<Flow<Location>>.analiseObserveLocation() = observeLocationScope?.launch{
        asyncSuccess { data, _ ->
            data?.collect { location ->
                emitHomeState(homeState = HomeState(location = location))
            }
        }
        error { error, _ ->
            isLocationObserved = false
            error?.printStackTrace()
        }
    }

    private fun stopObserveLocation() {
        observeLocationScope?.cancel()
        observeLocationScope = null
        isLocationObserved = false
    }

    private fun emitHomeState(homeState: HomeState) = viewModelScope.launch {
        _homeState.emit(value = homeState)
    }

    private fun getInitialHomeState(): HomeState = HomeState(
        location = Location(id = 0, latitude = 0.0, longitude = 0.0, time = 0)
    )
}