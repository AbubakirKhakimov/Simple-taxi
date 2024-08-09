package uz.abubakir_khakimov.simple_taxi.app

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import uz.abubakir_khakimov.simple_taxi.app.theme.SimpleTaxiTheme
import uz.abubakir_khakimov.simple_taxi.core.presentation.callback.PermissionManagerCallBack
import uz.abubakir_khakimov.simple_taxi.core.presentation.extensions.isServiceRunning
import uz.abubakir_khakimov.simple_taxi.core.presentation.managers.PermissionManager
import uz.abubakir_khakimov.simple_taxi.features.home.screens.HomeScreen
import uz.abubakir_khakimov.simple_taxi.features.home.services.LocationProviderService
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PermissionManagerCallBack {

    @Inject lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        permissionManager.registerActivityResult(activity = this, callBack = this)

        permissionManager.checkPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            autoAsk = true
        )

        setContent {
            SimpleTaxiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(paddingValues = innerPadding))
                }
            }
        }
    }

    override fun onActivityResult(result: Map<String, Boolean>, tag: Any) {
        if (result.containsValue(value = false)) return

        if (!isServiceRunning(serviceClass = LocationProviderService::class.java))
            runLocationProviderService()
    }

    private fun runLocationProviderService() = Intent(
        /* packageContext = */ applicationContext,
        /* cls = */ LocationProviderService::class.java
    ).also { intent ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(/* service = */ intent)
        else startService(/* service = */ intent)
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    SimpleTaxiTheme() {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HomeScreen(modifier = Modifier.padding(paddingValues = innerPadding))
        }
    }
}