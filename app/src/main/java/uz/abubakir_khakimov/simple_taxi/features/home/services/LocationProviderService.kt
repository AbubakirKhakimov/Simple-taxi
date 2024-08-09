package uz.abubakir_khakimov.simple_taxi.features.home.services

import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.location.Location
import uz.abubakir_khakimov.simple_taxi.domain.locations.models.Location as DataLocation
import android.os.*
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import uz.abubakir_khakimov.simple_taxi.R
import uz.abubakir_khakimov.simple_taxi.app.MainActivity
import uz.abubakir_khakimov.simple_taxi.core.presentation.callback.LocationManagerCallBack
import uz.abubakir_khakimov.simple_taxi.core.presentation.managers.LocationManager
import uz.abubakir_khakimov.simple_taxi.domain.locations.usecase.AddLocationUseCase
import javax.inject.Inject

@AndroidEntryPoint
class LocationProviderService : Service(), LocationManagerCallBack {

    @Inject lateinit var locationManager: LocationManager
    @Inject lateinit var addLocationUseCase: AddLocationUseCase

    private val serviceScope = CoroutineScope(context = SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        locationManager.startRealtimeLocation(context = this, locationManagerCallBack = this)
    }

    override fun locationChanged(newLocation: Location?) {
        if (newLocation == null) return

        serviceScope.launch {
            DataLocation(
                id = 0,
                latitude = newLocation.latitude,
                longitude = newLocation.longitude,
                bearing = newLocation.bearing,
                time = System.currentTimeMillis()
            ).also { location -> addLocationUseCase.invoke(location = location) }
        }
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showForegroundServiceNotification()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        stopForeground(/* notificationBehavior = */ STOP_FOREGROUND_REMOVE)
        locationManager.stopRealtimeLocation()
        serviceScope.cancel()
    }

    private fun showForegroundServiceNotification() {
        val notificationManager = getSystemService(
            /* name = */ Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(
            /* context = */ this,
            /* channelId = */ FOREGROUND_SERVICE_NOTIFICATION_CHANNEL_ID
        )
        val notificationClickEvent = Intent(
            /* packageContext = */ this,
            /* cls = */ MainActivity::class.java
        )
        val notificationClickEventPendingIntent = PendingIntent.getActivity(
            /* context = */ this,
            /* requestCode = */ 0,
            /* intent = */ notificationClickEvent,
            /* flags = */ FLAG_IMMUTABLE
        )

        notificationBuilder
            .setAutoCancel(/* autoCancel = */ false)
            .setDefaults(/* defaults = */ Notification.DEFAULT_ALL)
            .setCategory(/* category = */ Notification.CATEGORY_SERVICE)
            .setOngoing(/* ongoing = */ true)
            .setWhen(/* when = */ System.currentTimeMillis())
            .setSmallIcon(/* icon = */ R.mipmap.ic_launcher)
            .setPriority(/* pri = */ NotificationCompat.PRIORITY_LOW)
            .setContentTitle(
                /* title = */ getString(
                    /* resId = */ R.string.foreground_service_notification_title,
                    /* ...formatArgs = */ getString(/* resId = */ R.string.app_name)
                )
            )
            .setContentText(
                /* text = */ getString(
                    /* resId = */ R.string.foreground_service_notification_content
                )
            )
            .setContentIntent(/* intent = */ notificationClickEventPendingIntent)
            .setContentInfo("Info")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                /* id = */ FOREGROUND_SERVICE_NOTIFICATION_CHANNEL_ID,
                /* name = */ FOREGROUND_SERVICE_NOTIFICATION_CHANNEL_NAME,
                /* importance = */ NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(/* channel = */ notificationChannel)
        }

        startForeground(
            /* id = */ FOREGROUND_SERVICE_NOTIFICATION_ID,
            /* notification = */ notificationBuilder.build()
        )
    }

    companion object{

        const val FOREGROUND_SERVICE_NOTIFICATION_ID = 2002
        const val FOREGROUND_SERVICE_NOTIFICATION_CHANNEL_ID = "foreground_notification_chanel_id"
        const val FOREGROUND_SERVICE_NOTIFICATION_CHANNEL_NAME = "Location provider"
    }
}