package com.project.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScheduleItem(
    checked: Boolean,
    onCheckBoxClick: (Boolean) -> Unit
) {
    var checkState by remember { mutableStateOf(checked) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp),
            checked = checkState,
            onCheckedChange = {
                checkState = !checkState
                onCheckBoxClick(checkState)
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "title",
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "description",
                fontSize = 13.sp,
                fontWeight = FontWeight.Thin
            )
        }
    }
}

@Preview
@Composable
fun ScheduleItemPreview() {
    ScheduleItem(
        checked = false,
        onCheckBoxClick = {}
    )
}