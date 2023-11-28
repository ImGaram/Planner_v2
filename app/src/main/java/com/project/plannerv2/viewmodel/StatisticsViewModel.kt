package com.project.plannerv2.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.plannerv2.data.StatisticsData
import com.project.plannerv2.util.StatisticsMode

class StatisticsViewModel: ViewModel() {
    // 통계 데이터베이스 로직
    // 처음에는 없겠죠?
    // 처음에 일정을 생성할 때, total을 처음에 생성한 데이터 수로 정함
    // 갈수록 +하기
    // 삭제하면(추후에 추가 예정) -하기
    // 일요일까지 일정 정보를 수집하고 월요일 12시가 되면 일정 정보를 초기화하기(workManager)

    private fun modifyTotalData(
        addDataCount: Int,
        snapshot: DataSnapshot,
        reference: DatabaseReference
    ) {
        val data = snapshot.getValue(StatisticsData::class.java)
        if (data != null) {
            reference.setValue(StatisticsData(data.total+addDataCount, data.completed))
        } else reference.setValue(StatisticsData(total = addDataCount, completed = 0))
    }

    private fun modifyCompletedData(
        isCheck: Boolean,
        snapshot: DataSnapshot,
        reference: DatabaseReference
    ) {
        val data = snapshot.getValue(StatisticsData::class.java)
        if (data != null) {
            val setData = if (isCheck) +1 else -1
            reference.setValue(StatisticsData(data.total, data.completed + setData))
        }
    }

    fun modifyData(
        uid: String,
        addDataCount: Int = 0,
        isCheck: Boolean = false,
        mode: StatisticsMode
    ) {
        val reference = FirebaseDatabase.getInstance().reference
            .child("statistics")
            .child(uid)
            .child("this week")

        // todo :: 데이터베이스 set value, updateChildren의 결과에 따른 addOnComplete(onFailure)Listener를 한 함수에 관리하는 코드 추가하기
        reference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                when (mode) {
                    StatisticsMode.TOTAL -> modifyTotalData(addDataCount, snapshot, reference)
                    StatisticsMode.COMPLETED -> modifyCompletedData(isCheck, snapshot, reference)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}