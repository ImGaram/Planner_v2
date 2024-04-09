package com.toyproject.plannerv2.view.create.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toyproject.plannerv2.view.create.CreatePlanScreen

const val createPlanRoute = "createPlan"

fun NavHostController.navigateToCreatePlan() {
    this.navigate(createPlanRoute)
}

fun NavGraphBuilder.createPlanScreen(navigateToPlan: () -> Unit) {
    composable(route = createPlanRoute) {
        CreatePlanScreen(navigateToPlan = navigateToPlan)
    }
}