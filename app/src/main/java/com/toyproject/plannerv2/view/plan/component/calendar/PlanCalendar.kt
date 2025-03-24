package com.toyproject.plannerv2.view.plan.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun PlanHorizontalCalendar(
    modifier: Modifier = Modifier,
    currentDate: LocalDate = LocalDate.now(),
    config: PlanCalendarConfig = PlanCalendarConfig(),
    onSelectedDate: (LocalDate) -> Unit
) {
    val initialPage = (currentDate.year - config.yearRange.first) * 12 + currentDate.monthValue - 1
    val selectedDate = remember { mutableStateOf(currentDate) }
    val currentMonth = remember { mutableStateOf(YearMonth.now()) }
    val currentPage = remember { mutableIntStateOf(initialPage) }
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { (config.yearRange.last - config.yearRange.first) * 12 }
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        val addMonth = (pagerState.currentPage - currentPage.intValue).toLong()
        currentMonth.value = currentMonth.value.plusMonths(addMonth)
        currentPage.intValue = pagerState.currentPage
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val headerText = currentMonth.value.dateFormat("yyyy년 M월")

        CalendarHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            title = headerText,
            onRightArrowClick = {
                scope.launch {
                    val target = if (currentPage.intValue < pagerState.pageCount - 1) currentPage.intValue + 1
                    else currentPage.intValue

                    pagerState.animateScrollToPage(target)
                }
            },
            onLeftArrowClick = {
                scope.launch {
                    val target = if (currentPage.intValue > 0) currentPage.intValue - 1
                    else currentPage.intValue

                    pagerState.animateScrollToPage(target)
                }
            }
        )

        HorizontalPager(
            state = pagerState
        ) { page ->
            val date = LocalDate.of(
                config.yearRange.first + page / 12,
                page % 12 + 1,
                1
            )

            if (page in pagerState.currentPage - 1 .. pagerState.currentPage + 1) {
                CalendarMonthContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    currentDate = date,
                    selectedDate = selectedDate.value,
                    onSelectedDate = {
                        selectedDate.value = it
                        onSelectedDate(it)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PlanCalendarPreview() {
    Box(modifier = Modifier.background(Color.White)) {
        PlanHorizontalCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

        }
    }
}