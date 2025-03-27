package com.toyproject.plannerv2.view.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toyproject.plannerv2.view.login.LoginScreen

const val loginRoute = "login"

fun NavHostController.navigateToLogin() {
    this.navigate(loginRoute)
}

fun NavGraphBuilder.loginScreen(navigateToPlan: () -> Unit) {
    composable(route = loginRoute) {
        LoginScreen(navigateToPlan = navigateToPlan)
    }
}