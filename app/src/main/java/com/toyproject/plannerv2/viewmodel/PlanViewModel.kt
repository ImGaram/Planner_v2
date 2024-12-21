package com.toyproject.plannerv2.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.toyproject.plannerv2.data.CategoryData
import com.toyproject.plannerv2.data.PlanData
import com.toyproject.plannerv2.util.deleteFireStoreData
import com.toyproject.plannerv2.util.readFireStoreData
import com.toyproject.plannerv2.util.stringToUnixTimestamp
import com.toyproject.plannerv2.util.updateFireStoreData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlanViewModel: ViewModel() {
    private val _plans = MutableStateFlow<List<PlanData>>(emptyList())
    val plans = _plans.asStateFlow()

    private val _filterRollback = MutableStateFlow<List<PlanData>>(emptyList())

    fun getPlans(uid: String, date: String) {
        val getPlansRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        // baseDate와 날짜가 같은 document들만 불러오기.
        getPlansRef.whereEqualTo("baseDate", date.stringToUnixTimestamp()).readFireStoreData(
            onSuccess = {
                _plans.value = emptyList()  // 일정을 여러 번 불러와야 할 때,
                it.forEach { documentSnapshot ->
                    val planObj = documentSnapshot.toObject(PlanData::class.java)
                    if (planObj != null) {
                        val currentPlans = _plans.value.toMutableList()

                        currentPlans.add(planObj)
                        _plans.value = currentPlans
                        _filterRollback.value = currentPlans
                    }
                }
            }
        )
    }

    fun planCheck(uid: String, documentId: String) {
        val checkRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        checkRef.document(documentId).readFireStoreData(
            onSuccess = {
                val planInfo = it.data
                val completed = planInfo?.get("complete") as Boolean?

                if (completed != null) {
                    checkRef.document(documentId).updateFireStoreData(
                        updateValue = mapOf("complete" to !completed)
                    )
                }
            }
        )
    }

    fun modifyPlan(
        uid: String,
        documentId: String,
        title: String,
        description: String,
        onModifySuccess: () -> Unit = {},
        onModifyFailed: (Exception?) -> Unit = {}
    ) {
        val modifyPlanRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")
            .document(documentId)

        modifyPlanRef.updateFireStoreData(
            updateValue = mapOf(
                "title" to title,
                "description" to description
            ),
            onSuccess = onModifySuccess,
            onFailure = onModifyFailed
        )
    }

    fun deletePlan(
        uid: String,
        documentId: String,
        onDeleteSuccess: () -> Unit = {},
        onDeleteFailed: (Exception?) -> Unit = {}
    ) {
        val deletePlanRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")
            .document(documentId)

        deletePlanRef.deleteFireStoreData(
            onSuccess = onDeleteSuccess,
            onFailure = onDeleteFailed
        )
    }

    fun filterPlan(categoryData: CategoryData?) {
        if (categoryData == null) {
            _plans.value = _filterRollback.value
        } else {
            _plans.value = _filterRollback.value.filter { it.categories.containsKey(categoryData.id) }
        }
    }

    fun changePlanCompleteAtIndex(position: Int, checked: Boolean) {
        _plans.value.find { _plans.value.indexOf(it) == position }?.let { data ->
            data.complete = checked
        }
    }
}