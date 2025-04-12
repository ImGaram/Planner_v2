package com.toyproject.plannerv2.util

import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

fun String.stringToUnixTimestamp(): Long {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    return localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
}

fun Long.unixTimestampToLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun Long.unixTimestampToLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
}

// Color type 데이터를 hexCode 형식으로 변환
fun Color.toHexCode(): String {
    val alpha = alpha * 255
    val red = red * 255
    val green = green * 255
    val blue = blue * 255

    return String.format("#%02x%02x%02x%02x", alpha.toInt(), red.toInt(), green.toInt(), blue.toInt())
}

fun Context.openLink(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    startActivity(intent)
}