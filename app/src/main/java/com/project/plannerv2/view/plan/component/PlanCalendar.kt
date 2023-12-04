package com.project.plannerv2.view.plan.component

import android.widget.CalendarView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.project.plannerv2.util.DateDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PlanCalendar(
    date: MutableState<String?>,
    scope: CoroutineScope,
    dataStore: DateDataStore,
    onDateStateChange: () -> Unit,
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { CalendarView(it) }
    ) { calendarView ->
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        if (!date.value.isNullOrEmpty())
            calendarView.date = formatter.parse(date.value!!)!!.time

        calendarView.setOnDateChangeListener { _, year, month, day ->
            scope.launch {
                date.value = initSelectDate(year, month+1, day)
                dataStore.setDate(initSelectDate(year, month+1, day))
                onDateStateChange()
            }
        }
    }
}

private fun initSelectDate(year: Int, month: Int, day: Int): String {
    val initMonth = if (month < 10) "0$month" else month.toString()
    val initDay = if (day < 10) "0$day" else day.toString()

    return "$year-$initMonth-$initDay"
}