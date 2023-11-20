package com.project.plannerv2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.plannerv2.data.PlanData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreatePlanViewModel: ViewModel() {
    private val _planList = mutableStateListOf<PlanData>()
    val planList: SnapshotStateList<PlanData> = _planList

    private val _savePlan = MutableStateFlow(false)
    val savePlan: StateFlow<Boolean> = _savePlan.asStateFlow()

    fun addPlan(planData: PlanData) {
        _planList.add(planData)
    }

    fun modifyPlan(title: String, description: String, position: Int) {
        _planList[position] = PlanData(title, description)
    }

    fun removePlan(position: Int) {
        _planList.removeAt(position)
    }

    fun savePlan(plans: List<PlanData>, uid: String, date: String) {
        val saveFirebaseRef = FirebaseDatabase.getInstance().reference
            .child("schedule")
            .child(uid)
            .child(date)
        val objectMap = mutableMapOf<String, PlanData>()

        saveFirebaseRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { dataSnapShot ->
                    val data = dataSnapShot.getValue(PlanData::class.java)
                    objectMap[objectMap.size.toString()] = data!!
                }

                // 새로운 데이터를 add object에 추가하기
                plans.forEach { objectMap[objectMap.size.toString()] = it }

                // 추가한 데이터로 업데이트
                saveFirebaseRef.updateChildren(objectMap as Map<String, PlanData>).addOnCompleteListener { task ->
                    if (task.isSuccessful) _savePlan.value = true
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}