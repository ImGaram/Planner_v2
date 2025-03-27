package com.toyproject.plannerv2.view.statistics.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toyproject.plannerv2.view.statistics.StatisticsScreen

const val statisticsRoute = "statistics"

fun NavHostController.navigateToStatistics() {
    this.navigate(statisticsRoute)
}

fun NavGraphBuilder.statisticsScreen() {
    composable(route = statisticsRoute) {
        StatisticsScreen()
    }
}