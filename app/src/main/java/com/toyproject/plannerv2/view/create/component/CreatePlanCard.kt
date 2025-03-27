package com.toyproject.plannerv2.view.create.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toyproject.plannerv2.R
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme

@Composable
fun CreatePlanCard(
    onCardClick: () -> Unit
) {
    val stroke = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(10f, 10f), phase = 10f))
    val color = PlannerTheme.colors.gray400

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .drawBehind {
                drawRoundRect(
                    color = color,
                    style = stroke,
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }
            .clickable { onCardClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_create_schedule),
            contentDescription = "create schedule",
            tint = color
        )
    }
}

@Preview
@Composable
fun CreatePlanCardPreview() {
    CreatePlanCard {}
}