package com.toyproject.plannerv2.view.component.bottomnavigation

import com.toyproject.plannerv2.R
import com.toyproject.plannerv2.view.category.navigation.categoryRoute
import com.toyproject.plannerv2.view.plan.navigation.planRoute
import com.toyproject.plannerv2.view.statistics.navigation.statisticsRoute

sealed class BottomNavItem(
    val icon: Int, val route: String
) {
    object Calendar: BottomNavItem(R.drawable.ic_calendar, planRoute)
    object Category: BottomNavItem(R.drawable.ic_category, categoryRoute)
    object Statistics: BottomNavItem(R.drawable.ic_statistics, statisticsRoute)
}