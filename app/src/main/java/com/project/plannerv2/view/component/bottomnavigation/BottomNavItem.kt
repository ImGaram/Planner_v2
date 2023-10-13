package com.project.plannerv2.view.component.bottomnavigation

import com.project.plannerv2.R
import com.project.plannerv2.view.plan.navigation.planRoute
import com.project.plannerv2.view.statistics.navigation.statisticsRoute

sealed class BottomNavItem(
    val icon: Int, val route: String
) {
    object Calendar: BottomNavItem(R.drawable.ic_calendar, planRoute)
    object Statistics: BottomNavItem(R.drawable.ic_statistics, statisticsRoute)
}