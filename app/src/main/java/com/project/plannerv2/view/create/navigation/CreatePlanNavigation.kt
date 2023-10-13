package com.project.plannerv2.view.create.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.project.plannerv2.view.create.CreatePlanScreen

const val createPlanRoute = "createPlan"

fun NavHostController.navigateToCreatePlan() {
    this.navigate(createPlanRoute)
}

fun NavGraphBuilder.createPlanScreen() {
    composable(route = createPlanRoute) {
        CreatePlanScreen()
    }
}