package uz.abubakir_khakimov.simple_taxi.core.presentation.managers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import uz.abubakir_khakimov.simple_taxi.core.presentation.callback.LocationManagerCallBack

class LocationManagerImpl: LocationCallback(), LocationManager {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationManagerCallBack: LocationManagerCallBack? = null

    override fun onLocationResult(locResult: LocationResult) =
        locationManagerCallBack?.locationChanged(newLocation = locResult.lastLocation) ?: Unit

    override fun startRealtimeLocation(
        context: Context,
        locationManagerCallBack: LocationManagerCallBack
    ) {
        this.locationManagerCallBack = locationManagerCallBack
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(/* context = */ context)

        val locationRequest = LocationRequest
            .Builder(
                /* priority = */ Priority.PRIORITY_HIGH_ACCURACY,
                /* intervalMillis = */ LOCATION_REQUEST_INTERVAL
            )
            .setWaitForAccurateLocation(/* waitForAccurateLocation = */ true)
            .setMinUpdateIntervalMillis(/* minUpdateIntervalMillis = */ LOCATION_REQUEST_INTERVAL)
            .setMaxUpdateDelayMillis(/* maxUpdateDelayMillis = */ LOCATION_REQUEST_INTERVAL)
            .build()

        if (
            ActivityCompat.checkSelfPermission(
                /* context = */ context,
                /* permission = */ Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                /* context = */ context,
                /* permission = */ Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        fusedLocationProviderClient?.requestLocationUpdates(
            /* p0 = */ locationRequest,
            /* p1 = */ this,
            /* p2 = */ Looper.getMainLooper()
        )
    }

    override fun stopRealtimeLocation(){
        fusedLocationProviderClient?.removeLocationUpdates(/* p0 = */ this)
        fusedLocationProviderClient = null
        locationManagerCallBack = null
    }

    companion object{

        const val LOCATION_REQUEST_INTERVAL = 1000L
    }
}