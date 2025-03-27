package com.toyproject.plannerv2.view.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun LoginLoadingIndicator() {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0x80000000))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFF6EC4A7))
    }
}