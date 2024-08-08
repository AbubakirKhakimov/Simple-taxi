package uz.abubakir_khakimov.simple_taxi.app

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
import uz.abubakir_khakimov.simple_taxi.core.presentation.extensions.isServiceRunning
import uz.abubakir_khakimov.simple_taxi.features.home.screens.HomeScreen
import uz.abubakir_khakimov.simple_taxi.features.home.services.LocationProviderService

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (!isServiceRunning(serviceClass = LocationProviderService::class.java))
            runLocationProviderService()

        setContent {
            SimpleTaxiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(paddingValues = innerPadding))
                }
            }
        }
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