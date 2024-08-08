package uz.abubakir_khakimov.simple_taxi.features.home.screens

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.abubakir_khakimov.simple_taxi.R
import uz.abubakir_khakimov.simple_taxi.app.theme.GreenLight
import uz.abubakir_khakimov.simple_taxi.app.theme.LightPrimaryText
import uz.abubakir_khakimov.simple_taxi.app.theme.NightPrimaryText
import uz.abubakir_khakimov.simple_taxi.app.theme.RedLight
import uz.abubakir_khakimov.simple_taxi.app.theme.Typography
import uz.abubakir_khakimov.simple_taxi.app.theme.primaryTextColor

enum class TabItem(@StringRes val titleRes: Int, val indicatorPositionDp: Dp) {
    Busy(titleRes = R.string.tab_busy, indicatorPositionDp = 0.dp),
    Active(titleRes = R.string.tab_active, indicatorPositionDp = 100.dp)
}

@Composable
fun WorkStateTab(modifier: Modifier = Modifier, tabSelected: (TabItem) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(value = TabItem.Busy) }

    val indicatorOffset by animateDpAsState(
        targetValue = selectedTab.indicatorPositionDp, label = ""
    )

    Box(
        modifier = modifier
            .width(width = 200.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(size = 16.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .fillMaxHeight()
                .width(width = 100.dp)
                .padding(all = 4.dp)
                .background(
                    color = getIndicatorColorByTabItem(tabItem = selectedTab),
                    shape = RoundedCornerShape(size = 12.dp)
                )
        )

        Row(modifier = Modifier.fillMaxSize()) {
            Tab(
                modifier = Modifier
                    .weight(weight = 1f)
                    .padding(top = 4.dp, bottom = 4.dp, start = 4.dp, end = 2.dp),
                tabItem = TabItem.Busy,
                isSelected = (selectedTab == TabItem.Busy),
                onClick = { tabItem ->
                    selectedTab = tabItem
                    tabSelected(tabItem)
                }
            )

            Tab(
                modifier = Modifier
                    .weight(weight = 1f)
                    .padding(top = 4.dp, bottom = 4.dp, end = 4.dp, start = 2.dp),
                tabItem = TabItem.Active,
                isSelected = (selectedTab == TabItem.Active),
                onClick = { tabItem ->
                    selectedTab = tabItem
                    tabSelected(tabItem)
                }
            )
        }
    }
}

@Composable
fun Tab(
    modifier: Modifier = Modifier,
    tabItem: TabItem,
    isSelected: Boolean,
    onClick: (TabItem) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .clickable(onClick = { onClick(tabItem) }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = tabItem.titleRes),
            style = Typography.titleMedium,
            color = if (isSelected) getSelectedTitleColorByTabItem(tabItem = tabItem)
            else primaryTextColor(),
        )
    }
}

private fun getIndicatorColorByTabItem(tabItem: TabItem) = when (tabItem) {
    TabItem.Busy -> RedLight
    TabItem.Active -> GreenLight
}

private fun getSelectedTitleColorByTabItem(tabItem: TabItem) = when (tabItem) {
    TabItem.Busy -> NightPrimaryText
    TabItem.Active -> LightPrimaryText
}