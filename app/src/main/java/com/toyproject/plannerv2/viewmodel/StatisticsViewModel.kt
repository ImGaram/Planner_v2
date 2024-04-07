package com.toyproject.plannerv2.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.toyproject.plannerv2.data.StatisticsData
import com.toyproject.plannerv2.util.StatisticsMode
import com.toyproject.plannerv2.util.getFirebaseData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StatisticsViewModel: ViewModel() {
    // 통계 데이터베이스 로직
    // 처음에는 없겠죠?
    // 처음에 일정을 생성할 때, total을 처음에 생성한 데이터 수로 정함
    // 갈수록 +하기
    // 삭제하면(추후에 추가 예정) -하기

    private val _thisWeekStatistics = MutableStateFlow<StatisticsData?>(null)
    val thisWeekStatistics = _thisWeekStatistics.asStateFlow()

    fun getThisWeekStatistics(uid: String) {
        val thisWeekReference = FirebaseDatabase.getInstance().reference
            .child("statistics")
            .child(uid)
            .child("this week")

        thisWeekReference.getFirebaseData(
            onDataChangeLogic = { snapshot ->
                val statisticsData = snapshot.getValue(StatisticsData::class.java)

                if (statisticsData != null) {
                    _thisWeekStatistics.value = statisticsData
                }
            }
        )
    }

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
        val thisWeekReference = FirebaseDatabase.getInstance().reference
            .child("statistics")
            .child(uid)
            .child("this week")

        thisWeekReference.getFirebaseData(
            onDataChangeLogic = { snapshot ->
                when (mode) {
                    StatisticsMode.TOTAL -> modifyTotalData(addDataCount, snapshot, thisWeekReference)
                    StatisticsMode.COMPLETED -> modifyCompletedData(isCheck, snapshot, thisWeekReference)
                }
            }
        )
    }
}