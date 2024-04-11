package com.toyproject.plannerv2.util

import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryOf

// 여러 곳에서 사용되는 함수(공용 함수)만 모아놓은 곳.

// int list를 chart에 사용하기 위해 변환해줌
fun intListAsFloatEntryList(list: List<Int>): List<FloatEntry> {
    val floatEntryList = arrayListOf<FloatEntry>()
    floatEntryList.clear()

    list.forEachIndexed { index, item ->
        floatEntryList.add(entryOf(x = index.toFloat(), y = item.toFloat()))
    }

    return floatEntryList
}