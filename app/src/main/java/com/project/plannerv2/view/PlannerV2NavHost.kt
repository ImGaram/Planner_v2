package com.project.plannerv2.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun PlannerV2NavHost(
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navHostController, startDestination = startDestination) {

    }
}