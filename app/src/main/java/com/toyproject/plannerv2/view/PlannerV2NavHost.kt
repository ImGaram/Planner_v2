package com.toyproject.plannerv2.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.toyproject.plannerv2.view.create.navigation.createPlanScreen
import com.toyproject.plannerv2.view.create.navigation.navigateToCreatePlan
import com.toyproject.plannerv2.view.login.navigation.loginScreen
import com.toyproject.plannerv2.view.plan.navigation.navigateToPlan
import com.toyproject.plannerv2.view.plan.navigation.planScreen
import com.toyproject.plannerv2.view.statistics.navigation.statisticsScreen

@Composable
fun PlannerV2NavHost(
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        loginScreen(
            navigateToPlan = { navHostController.navigateToPlan() }
        )

        planScreen(
            navigateToCreatePlan = { navHostController.navigateToCreatePlan(it) }
        )

        createPlanScreen(
            navigateToPlan = { navHostController.navigateToPlan() }
        )

        statisticsScreen()
    }
}