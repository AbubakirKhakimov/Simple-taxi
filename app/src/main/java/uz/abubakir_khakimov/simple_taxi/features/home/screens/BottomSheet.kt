package uz.abubakir_khakimov.simple_taxi.features.home.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import uz.abubakir_khakimov.simple_taxi.R
import uz.abubakir_khakimov.simple_taxi.app.theme.Typography
import uz.abubakir_khakimov.simple_taxi.app.theme.primaryTextColor
import uz.abubakir_khakimov.simple_taxi.app.theme.secondaryIconColor
import uz.abubakir_khakimov.simple_taxi.app.theme.secondaryTextColor
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

sealed class BottomSheetState(val position: Float) {
    data object Expanded: BottomSheetState(position = 0f)
    data object PartlyExpanded: BottomSheetState(position = 260f)
    class Dragging(position: Float): BottomSheetState(position = position)
}

fun Float.toBottomSheetDragging(): BottomSheetState =
    BottomSheetState.Dragging(position = this)

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    bottomSheetState: MutableState<BottomSheetState> =
        mutableStateOf(value = BottomSheetState.PartlyExpanded)
) {
    val bottomSheetAnimatedOffsetY by animateFloatAsState(
        targetValue = bottomSheetState.value.position, label = ""
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .offset { IntOffset(x = 0, y = bottomSheetAnimatedOffsetY.roundToInt()) }
            .pointerInput(key1 = Unit) {
                verticalDragGesturesAction(bottomSheetState = bottomSheetState)
            }
    ) {
        HorizontalDivider(
            modifier = Modifier
                .width(width = 40.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.dp, bottom = 8.dp)
                .clip(shape = RoundedCornerShape(size = 16.dp))
                .alpha(alpha = 0.2f),
            thickness = 6.dp,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )

        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
        ) { BottomSheetInnerCard(modifier = modifier) }
    }
}

private suspend fun PointerInputScope.verticalDragGesturesAction(
    bottomSheetState: MutableState<BottomSheetState>
) = detectVerticalDragGestures(
    onDragEnd = {
        bottomSheetState.value = findOptimalState(
            bottomSheetOffsetY = bottomSheetState.value.position
        )
    },
    onVerticalDrag = { _, dragAmount ->
        bottomSheetState.value = (dragAmount + bottomSheetState.value.position)
            .coerceIn(
                minimumValue = BottomSheetState.Expanded.position,
                maximumValue = BottomSheetState.PartlyExpanded.position
            ).toBottomSheetDragging()
    }
)

private fun findOptimalState(bottomSheetOffsetY: Float): BottomSheetState {
    val expendedDif =
        BottomSheetState.Expanded.position.minus(other = bottomSheetOffsetY).absoluteValue
    val partlyExpandedDif =
        BottomSheetState.PartlyExpanded.position.minus(other = bottomSheetOffsetY).absoluteValue

    return if (expendedDif <= partlyExpandedDif) BottomSheetState.Expanded
    else BottomSheetState.PartlyExpanded
}

@Composable
fun BottomSheetInnerCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        shape = RoundedCornerShape(size = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Column {
            BottomSheetTab(
                iconRes = R.drawable.ic_switch,
                text = "Tarif",
                progressText = "6 / 8"
            )

            HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))

            BottomSheetTab(
                iconRes = R.drawable.ic_order,
                text = "Buyurtmalar",
                progressText = "0"
            )

            HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))

            BottomSheetTab(iconRes = R.drawable.ic_rocket, text = "Bordur")
        }
    }
}

@Composable
fun BottomSheetTab(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    text: String,
    progressText: String? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(all = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            tint = secondaryIconColor(),
            contentDescription = ""
        )

        Text(
            text = text,
            modifier = Modifier
                .weight(weight = 1f)
                .padding(start = 8.dp),
            style = Typography.titleMedium,
            color = primaryTextColor()
        )

        if (progressText != null) Text(
            text = progressText,
            style = Typography.titleMedium,
            color = secondaryTextColor()
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_right_chevron),
            contentDescription = "",
            modifier = Modifier.padding(start = 8.dp),
            tint = secondaryIconColor()
        )
    }
}