package com.toyproject.plannerv2.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.toyproject.plannerv2.view.category.navigation.categoryRoute
import com.toyproject.plannerv2.view.component.bottomnavigation.PlannerV2BottomNavigation
import com.toyproject.plannerv2.view.login.component.LoginLoadingIndicator
import com.toyproject.plannerv2.view.login.navigation.loginRoute
import com.toyproject.plannerv2.view.plan.navigation.planRoute
import com.toyproject.plannerv2.view.statistics.navigation.statisticsRoute
import com.toyproject.plannerv2.view.ui.theme.PlannerTheme
import com.toyproject.plannerv2.view.ui.theme.PlannerV2Theme
import com.toyproject.plannerv2.viewmodel.LoginViewModel
import com.toyproject.plannerv2.viewmodel.SplashViewModel

class MainActivity : ComponentActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()
    private val loginState: Boolean? by lazy { splashViewModel.loginState.value }
    private val splashState: Boolean by lazy { splashViewModel.splashState.value }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        actionBar?.hide()

        splashViewModel.checkLogin()
        installSplashScreen().setKeepOnScreenCondition { splashState }

        setContent {
            PlannerV2Theme {
                val navHostController = rememberNavController()
                val currentRoute by navHostController.currentBackStackEntryAsState()
                val isLogin = loginViewModel.isLogin.collectAsState()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PlannerTheme.colors.background)
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column {
                        Box(modifier = Modifier.weight(1f)) {
                            if (loginState != null) {
                                PlannerV2NavHost(
                                    navHostController = navHostController,
                                    startDestination = if (loginState!!) planRoute else loginRoute
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.height(1.dp),
                            color = PlannerTheme.colors.gray400
                        )

                        when (currentRoute?.destination?.route) {
                            planRoute, categoryRoute, statisticsRoute -> PlannerV2BottomNavigation(navHostController = navHostController)
                        }
                    }

                    if (isLogin.value == false) LoginLoadingIndicator()
                }
            }
        }
    }
}