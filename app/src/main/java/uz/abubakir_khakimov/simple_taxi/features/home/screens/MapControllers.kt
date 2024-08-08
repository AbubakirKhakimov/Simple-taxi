package uz.abubakir_khakimov.simple_taxi.features.home.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import uz.abubakir_khakimov.simple_taxi.R
import uz.abubakir_khakimov.simple_taxi.app.theme.BlueLight
import uz.abubakir_khakimov.simple_taxi.app.theme.secondaryIconColor
import uz.abubakir_khakimov.simple_taxi.core.presentation.extensions.mapRange
import kotlin.math.roundToInt

sealed class MapControllersState(val position: Float) {
    data object Expanded: MapControllersState(position = 0f)
    data object Collapsed: MapControllersState(position = 220f)
    class Dragging(position: Float): MapControllersState(position = position)
}

fun Float.toMapControllersDragging(): MapControllersState =
    MapControllersState.Dragging(position = this)

@Composable
fun MapControllers(
    modifier: Modifier = Modifier,
    mapControllersState: MapControllersState =
        MapControllersState.Expanded,
    camZoomPlusOnClick: () -> Unit = {},
    camZoomMinusOnClick: () -> Unit = {},
    currentLocationOnClick: () -> Unit = {}
) {
    val mapControllersAnimatedOffsetX by animateFloatAsState(
        targetValue = mapControllersState.position, label = ""
    )
    val mapControllersAnimatedAlpha by animateFloatAsState(
        targetValue = mapControllersState.position.mapRange(
            fromRangeStart = MapControllersState.Expanded.position,
            fromRangeEnd = MapControllersState.Collapsed.position,
            toRangeStart = 1f,
            toRangeEnd = 0f
        ),
        label = ""
    )

    Column(
        modifier = modifier
            .offset { IntOffset(x = mapControllersAnimatedOffsetX.roundToInt(), y = 0) }
            .alpha(alpha = mapControllersAnimatedAlpha)
    ) {
        FilledIconButton(
            onClick = camZoomPlusOnClick,
            modifier = modifier
                .padding(end = 16.dp)
                .size(size = 60.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                modifier = Modifier.size(size = 28.dp),
                tint = secondaryIconColor(),
                contentDescription = ""
            )
        }

        FilledIconButton(
            onClick = camZoomMinusOnClick,
            modifier = modifier
                .padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
                .size(size = 60.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_minus),
                modifier = Modifier.size(size = 28.dp),
                tint = secondaryIconColor(),
                contentDescription = ""
            )
        }

        FilledIconButton(
            onClick = currentLocationOnClick,
            modifier = modifier
                .padding(end = 16.dp)
                .size(size = 60.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_navigation),
                modifier = Modifier.size(size = 28.dp),
                tint = BlueLight,
                contentDescription = ""
            )
        }
    }
}