package com.toyproject.plannerv2.view.plan.component.calendar

import java.util.Locale

data class PlanCalendarConfig(
    val yearRange: IntRange = IntRange(1970, 2100),
    val locale: Locale = Locale.KOREAN
)
