package com.toyproject.plannerv2.view.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toyproject.plannerv2.view.setting.SettingScreen

const val settingRoute = "setting"

fun NavHostController.navigateToSetting() {
    navigate(settingRoute)
}

fun NavGraphBuilder.settingScreen(navigateToStatistics: () -> Unit) {
    composable(route = settingRoute) {
        SettingScreen(navigateToStatistics = navigateToStatistics)
    }
}