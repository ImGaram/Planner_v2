package com.toyproject.plannerv2.view.plan.component

import android.widget.CalendarView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun PlanCalendar(
    scope: CoroutineScope,
    onDateStateChange: (String) -> Unit,
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { CalendarView(it) }
    ) { calendarView ->
        calendarView.setOnDateChangeListener { _, year, month, day ->
            scope.launch {
                val selectedDate = LocalDate.of(year, month+1, day)
                onDateStateChange(selectedDate.toString())
            }
        }
    }
}