package com.toyproject.plannerv2.view.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.toyproject.plannerv2.view.component.progress.CircularProgressScreen
import com.toyproject.plannerv2.view.statistics.component.DailyStatisticsChart
import com.toyproject.plannerv2.view.statistics.component.StatisticsScoreCard
import com.toyproject.plannerv2.view.statistics.component.UserProfile
import com.toyproject.plannerv2.view.statistics.component.WeeklyCompletionStatisticsChart
import com.toyproject.plannerv2.viewmodel.StatisticsViewModel

@Composable
fun StatisticsScreen(statisticsViewModel: StatisticsViewModel = viewModel()) {
    val dailyStatisticsState = statisticsViewModel.dailyStatistics.collectAsState()
    val totalStatisticsState = statisticsViewModel.totalStatisticsData.collectAsState()
    val weeklyStatisticsState = statisticsViewModel.weeklyStatistics.collectAsState()
    val scrollState = rememberScrollState()

    val uid = FirebaseAuth.getInstance().uid
    LaunchedEffect(Unit) {
        if (uid != null) {
            statisticsViewModel.getDailyStatistics(uid)
            statisticsViewModel.getTotalStatistics(uid)
            statisticsViewModel.getWeeklyStatistics(uid)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        val currentAccount = GoogleSignIn.getLastSignedInAccount(LocalContext.current)
        UserProfile(
            modifier = Modifier.fillMaxWidth(),
            account = currentAccount
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            StatisticsScoreCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(15.dp),
                cardColor = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3)),
                title = "생성된 일정",
                body = totalStatisticsState.value?.total,
                bodyTextStyle = TextStyle(
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            StatisticsScoreCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 15.dp, end = 15.dp, bottom = 15.dp),
                cardColor = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3)),
                title = "완료된 일정",
                body = totalStatisticsState.value?.completed,
                bodyTextStyle = TextStyle(
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

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
            DailyStatisticsChart(
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

        // weeklyStatisticsState의 모든 데이터들이 null이 아닐 경우에만 차트를 띄우게 하기.
        if (weeklyStatisticsState.value.all { it != null }) {
            val totalPlanList = weeklyStatisticsState.value.map { it!!.total }.toList()
            val completedPlanList = weeklyStatisticsState.value.map { it!!.completed }.toList()
            WeeklyCompletionStatisticsChart(
                completedPlanList = totalPlanList,
                completedRateList = completedPlanList
            )
        } else CircularProgressScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    StatisticsScreen()
}