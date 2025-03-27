package com.toyproject.plannerv2.view.plan.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toyproject.plannerv2.view.plan.PlanScreen

const val planRoute = "plan"

fun NavHostController.navigateToPlan() {
    this.navigate(planRoute)
}

fun NavGraphBuilder.planScreen(navigateToCreatePlan: (String) -> Unit) {
    composable(route = planRoute) {
        PlanScreen(navigateToCreatePlan = navigateToCreatePlan)
    }
}