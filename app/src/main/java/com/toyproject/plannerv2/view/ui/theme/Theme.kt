package com.toyproject.plannerv2.view.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val PlannerDarkColors = PlannerColors(
    primary = PrimaryDark,
    background = BackgroundDark,
    cardBackground = CardBackgroundDark,
    gray100 = Gray100Dark,
    gray200 = Gray200Dark,
    green = GreenDark,
    yellow = YellowDark
)

private val PlannerLightColors = PlannerColors(
    primary = PrimaryLight,
    background = BackgroundLight,
    cardBackground = CardBackgroundLight,
    gray100 = Gray100Light,
    gray200 = Gray200Light,
    green = GreenLight,
    yellow = YellowLight
)

object PlannerTheme {
    val colors: PlannerColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

@Composable
private fun ProvidePlannerV2Theme(
    colors: PlannerColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)

    CompositionLocalProvider(
        LocalColors provides colorPalette,
        content = content
    )
}

@Composable
fun PlannerV2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = remember {
        if (darkTheme) PlannerDarkColors else PlannerLightColors
    }

    ProvidePlannerV2Theme(
        colors = colors,
        content = content
    )
}

val LocalColors = staticCompositionLocalOf { PlannerLightColors }
