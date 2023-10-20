package com.project.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScheduleHeader(
    month: String,
    day: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE7FFFD))
    ) {
        Text(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
            text = "${month}월 ${day}일 일정",
            fontSize = 15.sp
        )
    }
}

@Preview
@Composable
fun ScheduleHeaderPreview() {
    ScheduleHeader(month = "10", day = "15")
}