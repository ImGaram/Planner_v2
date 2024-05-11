package com.toyproject.plannerv2.view.category.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toyproject.plannerv2.view.category.CategoryScreen

const val categoryRoute = "category"

fun NavHostController.navigateToCategory() {
    this.navigate(categoryRoute)
}

fun NavGraphBuilder.categoryScreen() {
    composable(route = categoryRoute) {
        CategoryScreen()
    }
}