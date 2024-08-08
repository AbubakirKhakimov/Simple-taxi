package uz.abubakir_khakimov.simple_taxi.core.presentation.callback

import android.location.Location

interface LocationManagerCallBack{

    fun locationChanged(newLocation: Location?)
}