package com.project.plannerv2.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.plannerv2.view.component.bottomnavigation.PlannerV2BottomNavigation
import com.project.plannerv2.view.component.topbar.PlannerV2TopAppBar
import com.project.plannerv2.view.login.navigation.loginRoute
import com.project.plannerv2.view.plan.navigation.planRoute
import com.project.plannerv2.view.statistics.navigation.statisticsRoute
import com.project.plannerv2.view.ui.theme.PlannerV2Theme
import com.project.plannerv2.viewmodel.SplashViewModel

class MainActivity : ComponentActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()
    private val loginState: Boolean? by lazy { splashViewModel.loginState.value }
    private val splashState: Boolean by lazy { splashViewModel.splashState.value }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewModel.checkLogin()
        installSplashScreen().setKeepOnScreenCondition { splashState }

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
                            startDestination = if (loginState!!) planRoute else loginRoute
                        )
                    }
                }
            }
        }
    }
}