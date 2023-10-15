package com.project.plannerv2.view.plan

import android.widget.CalendarView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.project.plannerv2.view.plan.component.ScheduleHeader
import com.project.plannerv2.view.plan.component.ScheduleItem
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun PlanScreen() {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    val date = Date()

    var yearState by remember { mutableStateOf(formatter.format(date).split("-").first()) }
    var monthState by remember { mutableStateOf(formatter.format(date).split("-")[1]) }
    var dayState by remember { mutableStateOf(formatter.format(date).split("-").last()) }

    // change firebase date
    val list = listOf(1,2,3,4,5,6,7,8,9,10)
    val checkBoxList = listOf(true, false, false, false, false, false, false, false, false, true)

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { CalendarView(it) }
            ) { calendarView ->
                val selectedDate = "${yearState}-${monthState}-${dayState}"
                calendarView.date = formatter.parse(selectedDate, ParsePosition(0))!!.time

                calendarView.setOnDateChangeListener { _, year, month, day ->
                    yearState = year.toString()
                    monthState = (month + 1).toString()
                    dayState = day.toString()
                }
            }
        }

        stickyHeader {
            ScheduleHeader(month = monthState, day = dayState) {

            }
        }

        items(list) {
            ScheduleItem(
                checked = checkBoxList[it - 1],
                onCheckBoxClick = {

                }
            )

            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(horizontal = 15.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanScreenPreview() {
    PlanScreen()
}