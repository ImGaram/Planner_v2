package com.toyproject.plannerv2.view.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.toyproject.plannerv2.view.component.progress.CircularProgressScreen
import com.toyproject.plannerv2.view.statistics.component.ThisWeekPlanStatisticsChart
import com.toyproject.plannerv2.view.statistics.component.WeeklyCompletionStatisticsChart
import com.toyproject.plannerv2.viewmodel.StatisticsViewModel

// todo :: 기존 차트(이번 주 일정 차트)를 일간 일정 통계(완료한 일정만 카운트)로 변경하기, 기존 차트 데이터는 스코어카드 형식으로 빼기.
// todo :: 있으면 좋을 것 같은 UI 요소: 연속된 일정 생성 카운트
@Composable
fun StatisticsScreen(statisticsViewModel: StatisticsViewModel = viewModel()) {
    val dailyStatisticsState = statisticsViewModel.dailyStatistics.collectAsState()
    val scrollState = rememberScrollState()

    val uid = FirebaseAuth.getInstance().uid
    LaunchedEffect(Unit) {
        statisticsViewModel.getDailyStatistics(uid!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            modifier = Modifier.padding(15.dp),
            text = "일간 일정 통계",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Divider(
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 15.dp)
        )

        if (dailyStatisticsState.value.isNotEmpty()) {
            ThisWeekPlanStatisticsChart(
                dailyPlanList = dailyStatisticsState.value.toList()
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
        val completedPlanList = listOf(65, 23, 77, 146, 55)
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