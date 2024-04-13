package com.toyproject.plannerv2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.toyproject.plannerv2.data.PlanData
import com.toyproject.plannerv2.util.createFireStoreData
import com.toyproject.plannerv2.util.stringToUnixTimestamp
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

    fun modifyPlan(baseDate: String?, title: String, description: String, position: Int) {
        _planList[position] = PlanData(
            baseDate = baseDate?.stringToUnixTimestamp(),
            title = title,
            description = description
        )
    }

    fun removePlan(position: Int) {
        _planList.removeAt(position)
    }

    fun savePlan(plans: List<PlanData>, uid: String, baseDate: String?, navigateToPlan: () -> Unit) {
        val saveRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        plans.forEach { planData ->
            // 생성될 시점의 unix timestamp 구하기(document id, createdTime에 써먹기 위함)
            val savedTimeMillis = System.currentTimeMillis()
            val resultPlan = PlanData(
                baseDate = baseDate?.stringToUnixTimestamp(),
                title = planData.title,
                description = planData.description,
                createdTime = savedTimeMillis,
                complete = false
            )
            // documentLength와 생성된 plans들의 길이를 구해서 document의 name을 정함.
            saveRef.document(savedTimeMillis.toString()).createFireStoreData(
                setValue = resultPlan,
                onSuccess = navigateToPlan,
                onFailure = {}
            )
        }
    }
}