package com.toyproject.plannerv2.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PlanData(
    val title: String = "",
    val description: String = "",
    complete: Boolean = false
) {
    var complete by mutableStateOf(complete)
}