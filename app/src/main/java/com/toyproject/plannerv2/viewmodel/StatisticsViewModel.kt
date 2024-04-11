package com.toyproject.plannerv2.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.toyproject.plannerv2.data.PlanData
import com.toyproject.plannerv2.data.StatisticsData
import com.toyproject.plannerv2.util.StatisticsMode
import com.toyproject.plannerv2.util.readFireStoreData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class StatisticsViewModel: ViewModel() {
    private val _dailyStatistics = MutableStateFlow<Array<Int>>(emptyArray())
    val dailyStatistics = _dailyStatistics.asStateFlow()

    // 일간 일정을 불러온다.
    fun getDailyStatistics(uid: String) {
        val getThisWeekRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        val today = LocalDate.now()
        // 주의 첫날은 일요일, 주의 마지막 날은 토요일
        val firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        val lastDayOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
        // second가 아닌 millisecond의 timestmp를 받기 위함
        val timestampFirstDay = firstDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        val timestampLastDay = lastDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000

        getThisWeekRef.whereGreaterThan("createdTime", timestampFirstDay)
            .whereLessThan("createdTime", timestampLastDay)
            .readFireStoreData(
                onSuccess = {
                    // 날마다 일정의 생성 개수를 카운트할 array 생성
                    val dailyPlans = Array(7) { 0 }
                    var completedPlansCount = 0
                    it.forEach { documentSnapshot ->
                        val documentData = documentSnapshot.toObject<PlanData>()
                        val documentLocalDate = LocalDate.parse(documentData?.baseDate, DateTimeFormatter.ISO_DATE)

                        // 날짜에 따른 인덱스 수 +1 처리: 요일에 몇 개의 일정을 생성했는지 알 수 있음.
                        when (documentLocalDate.dayOfWeek) {
                            DayOfWeek.SUNDAY -> dailyPlans[0]++
                            DayOfWeek.MONDAY -> dailyPlans[1]++
                            DayOfWeek.TUESDAY -> dailyPlans[2]++
                            DayOfWeek.WEDNESDAY -> dailyPlans[3]++
                            DayOfWeek.THURSDAY -> dailyPlans[4]++
                            DayOfWeek.FRIDAY -> dailyPlans[5]++
                            DayOfWeek.SATURDAY -> dailyPlans[6]++
                        }
                        if (documentData?.complete == true) completedPlansCount++
                    }
                    // 하루에 생성한 총 일정 개수를 넣어주기.
                    _dailyStatistics.value = dailyPlans
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
//        val thisWeekReference = FirebaseDatabase.getInstance().reference
//            .child("statistics")
//            .child(uid)
//            .child("this week")
//
//        thisWeekReference.getFirebaseData(
//            onDataChangeLogic = { snapshot ->
//                when (mode) {
//                    StatisticsMode.TOTAL -> modifyTotalData(addDataCount, snapshot, thisWeekReference)
//                    StatisticsMode.COMPLETED -> modifyCompletedData(isCheck, snapshot, thisWeekReference)
//                }
//            }
//        )
    }
}