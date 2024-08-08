package uz.abubakir_khakimov.simple_taxi.features.home.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import uz.abubakir_khakimov.simple_taxi.R
import uz.abubakir_khakimov.simple_taxi.app.theme.secondaryIconColor
import uz.abubakir_khakimov.simple_taxi.core.presentation.extensions.mapRange
import kotlin.math.roundToInt

sealed class BottomSheetExpanderState(val position: Float) {
    data object Expanded: BottomSheetExpanderState(position = 0f)
    data object Collapsed: BottomSheetExpanderState(position = -220f)
    class Dragging(position: Float): BottomSheetExpanderState(position = position)
}

fun Float.toBottomSheetExpanderDragging(): BottomSheetExpanderState =
    BottomSheetExpanderState.Dragging(position = this)

@Composable
fun BottomSheetExpander(
    modifier: Modifier = Modifier,
    bottomSheetExpanderState: BottomSheetExpanderState =
        BottomSheetExpanderState.Expanded,
    onClick: () -> Unit = {}
) {
    val bottomSheetExpanderAnimatedOffsetX by animateFloatAsState(
        targetValue = bottomSheetExpanderState.position, label = ""
    )
    val bottomSheetExpanderAnimatedAlpha by animateFloatAsState(
        targetValue = bottomSheetExpanderState.position.mapRange(
            fromRangeStart = BottomSheetExpanderState.Expanded.position,
            fromRangeEnd = BottomSheetExpanderState.Collapsed.position,
            toRangeStart = 1f,
            toRangeEnd = 0f
        ),
        label = ""
    )

    Box(
        modifier = modifier
            .offset { IntOffset(x = bottomSheetExpanderAnimatedOffsetX.roundToInt(), y = 0) }
            .alpha(alpha = bottomSheetExpanderAnimatedAlpha)
    ) {
        OutlinedIconButton(
            onClick = onClick,
            modifier = modifier
                .padding(start = 16.dp)
                .size(size = 60.dp),
            shape = RoundedCornerShape(size = 16.dp),
            border = BorderStroke(width = 4.dp, color = MaterialTheme.colorScheme.primary),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevrons),
                modifier = Modifier.size(size = 28.dp),
                tint = secondaryIconColor(),
                contentDescription = ""
            )
        }
    }
}