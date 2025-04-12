package com.toyproject.plannerv2.view.setting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme

@Composable
fun SettingItem(
    onClick: () -> Unit,
    title: String,
    subTitle: String = ""
) {
    Box(modifier = Modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = PlannerTheme.colors.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = subTitle,
                color = PlannerTheme.colors.primary
            )
        }
    }
}

@Preview
@Composable
private fun SettingItemPreview() {
    SettingItem(
        onClick = {},
        title = "test"
    )
}