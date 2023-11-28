package com.project.plannerv2.view.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.project.plannerv2.view.component.progress.CircularProgressScreen
import com.project.plannerv2.view.statistics.component.ThisWeekPlanStatisticsChart
import com.project.plannerv2.view.statistics.component.WeeklyCompletionStatisticsChart
import com.project.plannerv2.viewmodel.StatisticsViewModel
import kotlin.math.roundToInt

@Composable
fun StatisticsScreen(statisticsViewModel: StatisticsViewModel = viewModel()) {
    val thisWeekStatisticsState = statisticsViewModel.thisWeekStatistics.collectAsState().value
    val scrollState = rememberScrollState()

    val uid = FirebaseAuth.getInstance().uid
    LaunchedEffect(Unit) {
        statisticsViewModel.getThisWeekStatistics(uid!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            modifier = Modifier.padding(15.dp),
            text = "이번 주 일정 통계",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Divider(
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 15.dp)
        )

        // firebase data 로 변경
        val totalPlanCount = thisWeekStatisticsState?.total
        val completedPlanCount = thisWeekStatisticsState?.completed

        if (totalPlanCount != null && completedPlanCount != null) {
            ThisWeekPlanStatisticsChart(
                totalPlanCount = totalPlanCount,
                completedPlanCount = completedPlanCount
            )

            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(horizontal = 15.dp)
            )

            val completionRate = completedPlanCount.toFloat() / totalPlanCount.toFloat() * 100f
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .background(Color(0xCCFFF6A7)),
                text = "이번 주 일정 완료율은 ${(completionRate * 10.0).roundToInt() / 10.0}% 입니다.",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        } else CircularProgressScreen()

        Text(
            modifier = Modifier.padding(15.dp),
            text = "주간 일정 통계",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Divider(
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 15.dp)
        )

        // firebase data 로 변경
        val completedPlanList = listOf(65, 23, 77, 136, 55)
        val completedRateList = listOf(100, 64, 89, 96, 77)
        WeeklyCompletionStatisticsChart(
            completedPlanList = completedPlanList,
            completedRateList = completedRateList
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    StatisticsScreen()
}