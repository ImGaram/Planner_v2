package com.toyproject.plannerv2.view.component.bottomnavigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme

@Composable
fun PlannerV2BottomNavigation(navHostController: NavHostController) {
    val bottomNavItems = listOf(
        BottomNavItem.Calendar,
        BottomNavItem.Category,
        BottomNavItem.Statistics
    )

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = PlannerTheme.colors.background
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navHostController.navigate(navItem.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = "navigation icon"
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PlannerTheme.colors.primary,
                    unselectedIconColor = PlannerTheme.colors.gray300,
                    indicatorColor = Color(0x00FAFAFA)
                )
            )
        }
    }
}