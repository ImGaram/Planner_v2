package com.toyproject.plannerv2.view.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val PlannerDarkColors = PlannerColors(
    primary = PrimaryDark,
    primaryVariant = PrimaryVariantDark,
    primaryA10 = PrimaryA10Dark,
    primaryA25 = PrimaryA25Dark,
    background = BackgroundDark,
    cardBackground = CardBackgroundDark,
    gray200 = Gray200Dark,
    gray300 = Gray300Dark,
    gray400 = Gray400Dark,
    red = RedDark,
    green = GreenDark,
    yellow = YellowDark,
    blue = BlueDark
)

private val PlannerLightColors = PlannerColors(
    primary = PrimaryLight,
    primaryVariant = PrimaryVariantLight,
    primaryA10 = PrimaryA10Light,
    primaryA25 = PrimaryA25Light,
    background = BackgroundLight,
    cardBackground = CardBackgroundLight,
    gray200 = Gray200Light,
    gray300 = Gray300Light,
    gray400 = Gray400Light,
    red = RedLight,
    green = GreenLight,
    yellow = YellowLight,
    blue = BlueLight
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
