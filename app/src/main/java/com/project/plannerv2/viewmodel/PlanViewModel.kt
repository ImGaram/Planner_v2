package com.project.plannerv2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.project.plannerv2.data.PlanData
import com.project.plannerv2.util.getFirebaseData

class PlanViewModel: ViewModel() {
    private val _plans = mutableStateListOf<PlanData>()
    val plans: List<PlanData> get() = _plans

    fun getPlans(uid: String, date: String) {
        val getPlanReference = FirebaseDatabase.getInstance().reference
            .child("schedule")
            .child(uid)
            .child(date)

        getPlanReference.getFirebaseData(
            onDataChangeLogic = { snapshot ->
                _plans.clear()
                snapshot.children.forEach { dataSnapshot ->
                    val data = dataSnapshot.getValue(PlanData::class.java)
                    _plans.add(data!!)
                }
            }
        )
    }

    fun planCheck(uid: String, date: String, position: String) {
        val changeCompleteStateReference = FirebaseDatabase.getInstance().reference
            .child("schedule")
            .child(uid)
            .child(date)
            .child(position)

        changeCompleteStateReference.getFirebaseData(
            onDataChangeLogic = { snapshot ->
                val targetPlan = snapshot.getValue(PlanData::class.java)
                val map = mutableMapOf<String, Boolean>()

                if (targetPlan != null) {
                    map["complete"] = !targetPlan.complete
                    changeCompleteStateReference.updateChildren(map as Map<String, Boolean>)
                }
            }
        )
    }

    fun changePlanCompleteAtIndex(position: Int, checked: Boolean) {
        _plans.find { _plans.indexOf(it) == position }?.let { data ->
            data.complete = checked
        }
    }
}