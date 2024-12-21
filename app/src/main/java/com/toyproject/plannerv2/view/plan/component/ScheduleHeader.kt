package com.toyproject.plannerv2.view.plan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toyproject.plannerv2.R
import com.toyproject.plannerv2.data.CategoryData

@Composable
fun ScheduleHeader(
    date: MutableState<String>,
    categories: List<CategoryData> = listOf(),
    onFilterSelected: (CategoryData?) -> Unit = {},
) {
    val filterExpanded = remember { mutableStateOf(false) }
    val filterSelected = remember { mutableStateOf("기본") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE7FFFD)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 5.dp, horizontal = 10.dp),
            text = "${parseDate(date.value)} 일정",
            fontSize = 15.sp
        )

        Box {
            Row(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { filterExpanded.value = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = "filter icon",
                    modifier = Modifier.padding(end = 4.dp),
                    tint = Color(0xFF6EC4A7)
                )

                Text(
                    text = filterSelected.value,
                    style = TextStyle(
                        fontSize = 15.sp
                    ),
                )
            }

            DropdownMenu(
                expanded = filterExpanded.value,
                onDismissRequest = { filterExpanded.value = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "기본") },
                    onClick = {
                        filterSelected.value = "기본"
                        onFilterSelected(null)
                        filterExpanded.value = false
                    }
                )

                for (i in categories.indices) {
                    DropdownMenuItem(
                        text = { Text(text = categories[i].categoryTitle) },
                        onClick = {
                            filterSelected.value = categories[i].categoryTitle
                            onFilterSelected(categories[i])
                            filterExpanded.value = false
                        }
                    )
                }
            }
        }
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