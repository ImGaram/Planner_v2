package com.toyproject.plannerv2.view.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val PrimaryLight = Color(0xFF000000)
val BackgroundLight = Color(0xFFF8F8F8)
val CardBackgroundLight = Color(0xFFF3F3F3)
val Gray100Light = Color(0x1AFFFFFF)
val Gray200Light = Color(0x40FFFFFF)
val GreenLight = Color(0xFF6EC4A7)
val YellowLight = Color(0xFFFFDB86)

val PrimaryDark = Color(0xFFFFFFFF)
val BackgroundDark = Color(0xFF080808)
val CardBackgroundDark = Color(0xFF1E1E1E)
val Gray100Dark = Color(0x1A000000)
val Gray200Dark = Color(0x40000000)
val GreenDark = Color(0xFF4A9E83)
val YellowDark = Color(0xFFD4B160)

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)

class PlannerColors(
    primary: Color,
    background: Color,
    cardBackground: Color,
    gray100: Color,
    gray200: Color,
    green: Color,
    yellow: Color
) {
    var primary by mutableStateOf(primary)
        private set
    var background by mutableStateOf(background)
        private set
    var cardBackground by mutableStateOf(cardBackground)
        private set
    var gray100 by mutableStateOf(gray100)
        private set
    var gray200 by mutableStateOf(gray200)
        private set
    var green by mutableStateOf(green)
        private set
    var yellow by mutableStateOf(yellow)
        private set

    fun copy() = PlannerColors(
        primary = primary,
        background = background,
        cardBackground = cardBackground,
        gray100 = gray100,
        gray200 = gray200,
        green = green,
        yellow = yellow
    )

    fun update(other: PlannerColors) {
        primary = other.primary
        background = other.background
        cardBackground = other.cardBackground
        gray100 = other.gray100
        gray200 = other.gray200
        green = other.green
        yellow = other.yellow
    }
}