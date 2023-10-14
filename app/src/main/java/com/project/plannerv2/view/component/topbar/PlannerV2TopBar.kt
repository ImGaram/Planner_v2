package com.project.plannerv2.view.component.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.project.plannerv2.R

@Composable
fun PlannerV2TopAppBar() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.bg_top_app_bar),
            contentDescription = "top app bar background"
        )
    }
}