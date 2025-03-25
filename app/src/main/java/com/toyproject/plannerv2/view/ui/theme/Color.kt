package com.toyproject.plannerv2.view.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val PrimaryLight = Color(0xFF000000)
val PrimaryVariantLight = Color(0xFFFFFFFF)
val PrimaryA10Light = Color(0x1A000000)
val PrimaryA25Light = Color(0x40000000)
val BackgroundLight = Color(0xFFF8F8F8)
val CardBackgroundLight = Color(0xFFF8F9FA)
val Gray200Light = Color(0xFFE9ECEf)
val Gray300Light = Color(0xFFDEE2E6)
val Gray400Light = Color(0xFFCED4DA)
val RedLight = Color(0xFFFF8686)
val GreenLight = Color(0xFF6EC4A7)
val YellowLight = Color(0xFFFFDB86)
val BlueLight = Color(0xFF86A4FF)

val PrimaryDark = Color(0xFFFFFFFF)
val PrimaryVariantDark = Color(0xFF000000)
val PrimaryA10Dark = Color(0x1AFFFFFF)
val PrimaryA25Dark = Color(0x40FFFFFF)
val BackgroundDark = Color(0xFF080808)
val CardBackgroundDark = Color(0xFF1E1E1E)
val Gray200Dark = Color(0xFF2D2D2D)
val Gray300Dark = Color(0xFF3A3A3A)
val Gray400Dark = Color(0xFF484848)
val RedDark = Color(0xFFB84D4D)
val GreenDark = Color(0xFF4A9E83)
val YellowDark = Color(0xFFD4B160)
val BlueDark = Color(0xFF728BD5)

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)

class PlannerColors(
    primary: Color,
    primaryVariant: Color,
    primaryA10: Color,
    primaryA25: Color,
    background: Color,
    cardBackground: Color,
    gray200: Color,
    gray300: Color,
    gray400: Color,
    red: Color,
    green: Color,
    yellow: Color,
    blue: Color
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var primaryA10 by mutableStateOf(primaryA10)
        private set
    var primaryA25 by mutableStateOf(primaryA25)
        private set
    var background by mutableStateOf(background)
        private set
    var cardBackground by mutableStateOf(cardBackground)
        private set
    var gray200 by mutableStateOf(gray200)
        private set
    var gray300 by mutableStateOf(gray300)
        private set
    var gray400 by mutableStateOf(gray400)
        private set
    var red by mutableStateOf(red)
        private set
    var green by mutableStateOf(green)
        private set
    var yellow by mutableStateOf(yellow)
        private set
    var blue by mutableStateOf(blue)
        private set

    fun update(other: PlannerColors) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        primaryA10 = other.primaryA10
        primaryA25 = other.primaryA25
        background = other.background
        cardBackground = other.cardBackground
        gray200 = other.gray200
        gray300 = other.gray300
        gray400 = other.gray400
        red = other.red
        green = other.green
        yellow = other.yellow
        blue = other.blue
    }
}