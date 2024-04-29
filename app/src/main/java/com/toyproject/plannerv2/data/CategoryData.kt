package com.toyproject.plannerv2.data

data class CategoryData(
    val categoryTitle: String = "",
    val savedPlanCount: Int = 0,
    val categoryColorHex: String = "",
    val createdTime: Long? = null
)
