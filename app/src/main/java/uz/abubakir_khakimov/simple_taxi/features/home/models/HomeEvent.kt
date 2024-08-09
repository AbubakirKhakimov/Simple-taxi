package uz.abubakir_khakimov.simple_taxi.features.home.models

sealed class HomeEvent {

    data object ObserveLocation : HomeEvent()
}