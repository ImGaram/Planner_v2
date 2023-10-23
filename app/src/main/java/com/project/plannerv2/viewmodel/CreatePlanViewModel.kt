package com.project.plannerv2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.project.plannerv2.data.PlanData

class CreatePlanViewModel: ViewModel() {
    private val _planList = mutableStateListOf<PlanData>()
    val planList: SnapshotStateList<PlanData> = _planList

    fun addPlan(planData: PlanData) {
        _planList.add(planData)
    }

    fun modifyPlan(title: String, description: String, position: Int) {
        _planList[position] = PlanData(title, description)
    }

    fun removePlan(position: Int) {
        _planList.removeAt(position)
    }
}