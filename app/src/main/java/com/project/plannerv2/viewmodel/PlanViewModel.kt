package com.project.plannerv2.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.plannerv2.data.PlanData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlanViewModel: ViewModel() {
    private val _plans = MutableStateFlow<List<PlanData>?>(null)
    val plans: StateFlow<List<PlanData>?> = _plans.asStateFlow()

    fun getPlans(uid: String, date: String) {
        val getPlanReference = FirebaseDatabase.getInstance().reference
            .child("schedule")
            .child(uid)
            .child(date)

        getPlanReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val planList = mutableListOf<PlanData>()
                snapshot.children.forEach { dataSnapshot ->
                    val data = dataSnapshot.getValue(PlanData::class.java)
                    planList.add(data!!)
                }

                _plans.value = planList
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}