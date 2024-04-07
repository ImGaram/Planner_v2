package com.toyproject.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScheduleHeader(date: MutableState<String?>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE7FFFD))
    ) {
        Text(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
            text = "${parseDate(date.value!!)} 일정",
            fontSize = 15.sp
        )
    }
}

private fun parseDate(date: String): String {
    return if (date.isNotEmpty()) {
        val monthString = date.split("-")[1]
        val dayString = date.split("-").last()

        val month = if (monthString.toInt() < 10)
            monthString.substring(1)
        else monthString

        val day = if (dayString.toInt() < 10)
            dayString.substring(1)
        else dayString

        "${month}월 ${day}일"
    } else ""
}

@Preview
@Composable
fun ScheduleHeaderPreview() {
    ScheduleHeader(date = remember { mutableStateOf("2023-01-01") })
}