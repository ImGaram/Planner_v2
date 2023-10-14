package com.project.plannerv2.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.plannerv2.view.component.bottomnavigation.PlannerV2BottomNavigation
import com.project.plannerv2.view.component.topbar.PlannerV2TopAppBar
import com.project.plannerv2.view.login.navigation.loginRoute
import com.project.plannerv2.view.plan.navigation.planRoute
import com.project.plannerv2.view.statistics.navigation.statisticsRoute
import com.project.plannerv2.view.ui.theme.PlannerV2Theme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlannerV2Theme {
                val navHostController = rememberNavController()
                val currentRoute by navHostController.currentBackStackEntryAsState()

                Scaffold(
                    topBar = { PlannerV2TopAppBar() },
                    bottomBar = {
                        when (currentRoute?.destination?.route) {
                            planRoute, statisticsRoute -> PlannerV2BottomNavigation(navHostController = navHostController)
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        PlannerV2NavHost(
                            navHostController = navHostController,
                            startDestination = loginRoute
                        )
                    }
                }
            }
        }
    }
}