package com.toyproject.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun PlanMenuItem(
    titleIconImageVector: ImageVector,
    titleIconTint: Color = Color.Black,
    menuTitle: String,
    menuClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 15.dp)
            .clickable { menuClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(10.dp),
            imageVector = titleIconImageVector,
            tint = titleIconTint,
            contentDescription = "plan menu icon"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = menuTitle
        )
    }
}