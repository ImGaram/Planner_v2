package com.toyproject.plannerv2.view.statistics

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import com.toyproject.plannerv2.viewmodel.StatisticsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatisticsScreen(
    statisticsViewModel: StatisticsViewModel = viewModel(),
    navigateToSetting: () -> Unit
) {
    val dailyStatisticsState = statisticsViewModel.dailyStatistics.collectAsState()
    val totalStatisticsState = statisticsViewModel.totalStatisticsData.collectAsState()
    val weeklyStatisticsState = statisticsViewModel.weeklyStatistics.collectAsState()
    val weeklyLoadCntState = statisticsViewModel.weeklyLoadCount.collectAsState()

    val uid = FirebaseAuth.getInstance().uid
    val currentAccount = GoogleSignIn.getLastSignedInAccount(LocalContext.current)

    LaunchedEffect(Unit) {
        if (uid != null) {
            statisticsViewModel.getDailyStatistics(uid)
            statisticsViewModel.getTotalStatistics(uid)
            statisticsViewModel.getWeeklyStatistics(uid)
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PlannerTheme.colors.background)
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "통계",
                    style = TextStyle(
                        color = PlannerTheme.colors.primary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                IconButton(
                    onClick = navigateToSetting,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "setting icon",
                        modifier = Modifier.size(24.dp),
                        tint = PlannerTheme.colors.primary
                    )
                }
            }
        }

        item {
            UserProfile(
                modifier = Modifier.fillMaxWidth(),
                account = currentAccount
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                StatisticsScoreCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(15.dp),
                    cardColor = CardDefaults.cardColors(containerColor = PlannerTheme.colors.gray200),
                    title = "생성된 일정",
                    body = totalStatisticsState.value?.total,
                    titleTextStyle = TextStyle(
                        color = PlannerTheme.colors.primary
                    ),
                    bodyTextStyle = TextStyle(
                        color = PlannerTheme.colors.primary,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                StatisticsScoreCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 15.dp, end = 15.dp, bottom = 15.dp),
                    cardColor = CardDefaults.cardColors(containerColor = PlannerTheme.colors.gray200),
                    title = "완료된 일정",
                    body = totalStatisticsState.value?.completed,
                    titleTextStyle = TextStyle(
                        color = PlannerTheme.colors.primary
                    ),
                    bodyTextStyle = TextStyle(
                        color = PlannerTheme.colors.primary,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        item {
            Text(
                modifier = Modifier.padding(15.dp),
                text = "일간 일정 통계",
                color = PlannerTheme.colors.primary,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(horizontal = 15.dp),
                color = PlannerTheme.colors.gray300
            )
        }

        item {
            if (dailyStatisticsState.value.isNotEmpty()) {
                DailyStatisticsChart(
                    dailyPlanList = dailyStatisticsState.value.toList()
                )
            } else CircularProgressScreen()
        }

        item {
            Text(
                modifier = Modifier.padding(15.dp),
                text = "주간 일정 통계",
                color = PlannerTheme.colors.primary,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(horizontal = 15.dp),
                color = PlannerTheme.colors.gray300
            )
        }

        item {
            // weeklyStatisticsState의 모든 데이터들이 null이 아닐 경우에만 차트를 띄우게 하기.
            if (weeklyLoadCntState.value >= 5) {
                val totalPlanList = weeklyStatisticsState.value.map { it!!.total }.toList()
                val completedPlanList = weeklyStatisticsState.value.map { it!!.completed }.toList()
                WeeklyCompletionStatisticsChart(
                    totalPlanList = totalPlanList,
                    completedPlanList = completedPlanList
                )
            } else CircularProgressScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    StatisticsScreen {}
}