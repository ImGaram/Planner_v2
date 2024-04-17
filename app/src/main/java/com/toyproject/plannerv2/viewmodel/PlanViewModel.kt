package com.toyproject.plannerv2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.toyproject.plannerv2.data.PlanData
import com.toyproject.plannerv2.util.deleteFireStoreData
import com.toyproject.plannerv2.util.readFireStoreData
import com.toyproject.plannerv2.util.stringToUnixTimestamp
import com.toyproject.plannerv2.util.updateFireStoreData
import java.lang.Exception

class PlanViewModel: ViewModel() {
    private val _plans = mutableStateListOf<PlanData>()
    val plans: List<PlanData> get() = _plans

    fun getPlans(uid: String, date: String) {
        val getPlansRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        // baseDate와 날짜가 같은 document들만 불러오기.
        getPlansRef.whereEqualTo("baseDate", date.stringToUnixTimestamp()).readFireStoreData(
            onSuccess = {
                _plans.clear()
                it.forEach { documentSnapshot ->
                    val planObj = documentSnapshot.toObject(PlanData::class.java)
                    if (planObj != null) _plans.add(planObj)
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

    fun deletePlan(uid: String, documentId: String) {
        val deletePlanRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")
            .document(documentId)

        deletePlanRef.deleteFireStoreData(
            onSuccess = {
                val deleteTarget = _plans.find { it.createdTime.toString() == documentId }
                _plans.remove(deleteTarget)
            }
        )
    }

    fun changePlanCompleteAtIndex(position: Int, checked: Boolean) {
        _plans.find { _plans.indexOf(it) == position }?.let { data ->
            data.complete = checked
        }
    }
}