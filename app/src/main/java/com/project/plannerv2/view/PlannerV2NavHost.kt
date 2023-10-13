package com.project.plannerv2.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.project.plannerv2.view.create.navigation.createPlanScreen
import com.project.plannerv2.view.login.navigation.loginScreen
import com.project.plannerv2.view.plan.navigation.planScreen
import com.project.plannerv2.view.statistics.navigation.statisticsScreen

@Composable
fun PlannerV2NavHost(
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        loginScreen()

        planScreen()

        createPlanScreen()

        statisticsScreen()
    }
}