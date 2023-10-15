package com.project.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.plannerv2.R

@Composable
fun ScheduleHeader(
    month: String,
    day: String,
    iconClick: () -> Unit
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

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .clickable { iconClick() },
            painter = painterResource(id = R.drawable.ic_add_schedule),
            contentDescription = "add schedule icon"
        )
    }
}

@Preview
@Composable
fun ScheduleHeaderPreview() {
    ScheduleHeader(month = "10", day = "15") {}
}