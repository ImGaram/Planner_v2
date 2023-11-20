package com.project.plannerv2.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

                val initDataStoreState = remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    splashViewModel.initDataStore(initDataStoreState)
                }

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
                        if (initDataStoreState.value) {
                            if (loginState != null) {
                                PlannerV2NavHost(
                                    navHostController = navHostController,
                                    startDestination = if (loginState!!) planRoute else loginRoute
                                )
                            }
                        } else CircularProgressScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun CircularProgressScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF6EC4A7))
        }
    }
}