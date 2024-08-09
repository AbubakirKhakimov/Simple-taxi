package uz.abubakir_khakimov.simple_taxi.features.home.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import uz.abubakir_khakimov.simple_taxi.R
import uz.abubakir_khakimov.simple_taxi.app.theme.GreenLight
import uz.abubakir_khakimov.simple_taxi.app.theme.LightPrimaryText
import uz.abubakir_khakimov.simple_taxi.app.theme.Typography
import uz.abubakir_khakimov.simple_taxi.app.theme.primaryIconColor

@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    menuOnClick: () -> Unit = {},
    workStateTabSelected: (TabItem) -> Unit = {}
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (menuRef, workStateTabRef, speedLimitRef) = createRefs()

        createHorizontalChain(
            menuRef.withChainParams(startMargin = 16.dp),
            workStateTabRef.withHorizontalChainParams(startMargin = 16.dp, endMargin = 16.dp),
            speedLimitRef.withChainParams(endMargin = 16.dp),
            chainStyle = ChainStyle.SpreadInside
        )

        FilledIconButton(
            onClick = menuOnClick,
            modifier = Modifier
                .constrainAs(ref = menuRef) {
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = workStateTabRef.start)
                    top.linkTo(anchor = parent.top, margin = 16.dp)
                    width = Dimension.value(dp = 60.dp)
                    height = Dimension.value(dp = 60.dp)
                },
            shape = RoundedCornerShape(size = 16.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                modifier = Modifier.size(size = 28.dp),
                tint = primaryIconColor(),
                contentDescription = ""
            )
        }

        WorkStateTab(
            modifier = Modifier.constrainAs(ref = workStateTabRef) {
                start.linkTo(anchor = menuRef.end)
                end.linkTo(anchor = speedLimitRef.start)
                top.linkTo(anchor = parent.top, margin = 16.dp)
                height = Dimension.value(dp = 60.dp)
            },
            tabSelected = workStateTabSelected
        )

        Card(
            modifier = Modifier
                .constrainAs(ref = speedLimitRef) {
                    end.linkTo(anchor = parent.end)
                    start.linkTo(anchor = workStateTabRef.end)
                    top.linkTo(anchor = parent.top, margin = 16.dp)
                    width = Dimension.value(dp = 60.dp)
                    height = Dimension.value(dp = 60.dp)
                },
            shape = RoundedCornerShape(size = 16.dp),
            border = BorderStroke(width = 4.dp, color = MaterialTheme.colorScheme.primary),
            colors = CardDefaults.cardColors(containerColor = GreenLight)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "95",
                    style = Typography.titleLarge,
                    color = LightPrimaryText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}