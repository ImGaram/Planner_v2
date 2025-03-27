package com.toyproject.plannerv2.view.plan.component.calendar

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

fun YearMonth.dateFormat(pattern: String, locale: Locale = Locale.getDefault()): String {
    return format(DateTimeFormatter.ofPattern(pattern, locale))
}

fun LocalDate.dateFormat(pattern: String, locale: Locale = Locale.getDefault()): String {
    return format(DateTimeFormatter.ofPattern(pattern, locale))
}