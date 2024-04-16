package com.toyproject.plannerv2.view.create.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.toyproject.plannerv2.view.create.CreatePlanScreen

const val createPlanRoute = "createPlan"

fun NavHostController.navigateToCreatePlan(selectedDate: String) {
    this.navigate("$createPlanRoute/$selectedDate")
}

fun NavGraphBuilder.createPlanScreen(navigateToPlan: () -> Unit) {
    composable(
        route = "$createPlanRoute/{selectedDate}",
        arguments = listOf(
            navArgument("selectedDate") { type = NavType.StringType }
        )
    ) {
        val selectedDate = it.arguments?.getString("selectedDate")
        CreatePlanScreen(
            selectedDate = selectedDate,
            navigateToPlan = navigateToPlan
        )
    }
}