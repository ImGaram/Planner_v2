package com.project.plannerv2.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.project.plannerv2.view.login.navigation.loginRoute
import com.project.plannerv2.view.ui.theme.PlannerV2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlannerV2Theme {
                PlannerV2NavHost(
                    navHostController = rememberNavController(),
                    startDestination = loginRoute
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}