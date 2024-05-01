package com.toyproject.plannerv2.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PlanData(
    val baseDate: Long? = null,
    val title: String = "",
    val description: String = "",
    val createdTime: Long? = null,
    category: Map<String, Map<String, Any>> = mapOf(),
    complete: Boolean = false,
    isMenuOpen: Boolean = false
) {
    var complete by mutableStateOf(complete)
    var isMenuOpen by mutableStateOf(isMenuOpen)
    var category by mutableStateOf(category)
}