package com.toyproject.plannerv2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.toyproject.plannerv2.data.PlanData
import com.toyproject.plannerv2.data.StatisticsData
import com.toyproject.plannerv2.util.readFireStoreData
import com.toyproject.plannerv2.util.stringToUnixTimestamp
import com.toyproject.plannerv2.util.unixTimestampToLocalDate
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

    private val _totalStatisticsData = MutableStateFlow<StatisticsData?>(StatisticsData())
    val totalStatisticsData = _totalStatisticsData.asStateFlow()

    private val _weeklyStatistics = mutableStateListOf<StatisticsData>()
    val weeklyStatistics: List<StatisticsData> get() = _weeklyStatistics

    // 일간 일정을 불러온다.
    fun getDailyStatistics(uid: String) {
        val getDailyRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        val today = LocalDate.now()
        // 주의 첫날은 일요일, 주의 마지막 날은 토요일
        // 당일 기준 다음주 첫날인 일요일을 마지막으로 정함.
        // 이유: 토요일 동안의 일정 생성 여부를 확인해야 하는데 토요일로 기준을 잡으면 토요일 오전 12시가 마지막날 기준이 되기 때문
        val firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        val lastDayOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        // second가 아닌 millisecond의 timestmp를 받기 위함
        val timestampFirstDay = firstDayOfWeek.toString().stringToUnixTimestamp()
        val timestampLastDay = lastDayOfWeek.toString().stringToUnixTimestamp()

        getDailyRef.whereGreaterThan("createdTime", timestampFirstDay)
            .whereLessThan("createdTime", timestampLastDay)
            .readFireStoreData(
                onSuccess = {
                    // 날마다 일정의 생성 개수를 카운트할 array 생성
                    val dailyPlans = Array(7) { 0 }
                    var completedPlansCount = 0
                    it.forEach { documentSnapshot ->
                        val documentData = documentSnapshot.toObject<PlanData>()
                        val documentLocalDate = documentData?.baseDate?.unixTimestampToLocalDate()

                        if (documentLocalDate != null) {
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
                        }
                        if (documentData?.complete == true) completedPlansCount++
                    }
                    // 하루에 생성한 총 일정 개수를 넣어주기.
                    _dailyStatistics.value = dailyPlans
                }
            )
    }

    // 전체 일정에 대한 정보를 불러온다.
    fun getTotalStatistics(uid: String) {
        val getTotalRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        getTotalRef.readFireStoreData(
            onSuccess = {
                var completedPlanCount = 0
                it.forEach { documentSnapshot ->
                    val isPlanCompleted = documentSnapshot.data?.get("complete") as Boolean?
                    if (isPlanCompleted == true) completedPlanCount++
                }

                _totalStatisticsData.value = StatisticsData(
                    total = it.size,
                    completed = completedPlanCount
                )
            }
        )
    }

    fun getWeeklyStatistics(uid: String) {
        val getWeeklyRef = FirebaseFirestore.getInstance()
            .collection("schedule")
            .document(uid)
            .collection("plans")

        // 화면 전환 할 때 일정이 쌓이지 않게 하기 위한 list clear.
        _weeklyStatistics.clear()

        val today = LocalDate.now()
        // 당일 기준 5주 전까지의 날짜를 구함
        repeat(5) { minusWeek ->
            // n주 전 날짜를 구한다(당일 기준)
            val todayWeekAgo = today.minusWeeks((minusWeek + 1).toLong())
            // 주의 첫날은 일요일, 주의 마지막 날은 토요일
            // 당일 기준 다음주 첫날인 일요일을 마지막으로 정함.
            // 이유: 토요일 동안의 일정 생성 여부를 확인해야 하는데 토요일로 기준을 잡으면 토요일 오전 12시가 마지막날 기준이 되기 때문
            val firstDayOfWeek = todayWeekAgo.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
            val lastDayOfWeek = todayWeekAgo.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            // second가 아닌 millisecond의 timestmp를 받기 위함
            val timestampFirstDay = firstDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
            val timestampLastDay = lastDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000

            getWeeklyRef.whereGreaterThan("createdTime", timestampFirstDay)
                .whereLessThan("createdTime", timestampLastDay)
                .readFireStoreData(
                    onSuccess = {
                        var completedPlanCount = 0
                        it.forEach { documentSnapshot ->
                            val isPlanCompleted = documentSnapshot.data?.get("complete") as Boolean?
                            if (isPlanCompleted == true) completedPlanCount++
                        }

                        // todo :: 주간 일정의 순서를 올바르게 하도록 만들기.
                        _weeklyStatistics.add(
                            StatisticsData(
                                total = it.size,
                                completed = completedPlanCount
                            )
                        )
                    }
                )
        }
    }
}