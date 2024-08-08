package uz.abubakir_khakimov.simple_taxi.app.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = NightPrimary,
    primaryContainer = NightPrimaryAlpha80,
    secondary = NightSecondary,
    secondaryContainer = NightSecondaryAlpha80,
    surface = NightSurface,
    onSurface = NightOnSurface,
    outline = NightDivider
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    primaryContainer = LightPrimaryAlpha80,
    secondary = LightSecondary,
    secondaryContainer = LightSecondaryAlpha80,
    surface = LightSurface,
    onSurface = LightOnSurface,
    outline = LightDivider
)

@Composable
fun primaryTextColor() = if (isSystemInDarkTheme()) NightPrimaryText else LightPrimaryText

@Composable
fun secondaryTextColor() = if (isSystemInDarkTheme()) NightSecondaryText else LightSecondaryText

@Composable
fun primaryIconColor() = if (isSystemInDarkTheme()) NightPrimaryIcon else LightPrimaryIcon

@Composable
fun secondaryIconColor() = if (isSystemInDarkTheme()) NightSecondaryIcon else LightSecondaryIcon

@Composable
fun SimpleTaxiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}