package com.toyproject.plannerv2.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateDataStore(private val context: Context) {
    companion object {
        // 데이터 저장에 사용할 key 정의
        private val Context.dateDataStore: DataStore<Preferences> by preferencesDataStore(name = "selectedDate")
        val DATE_KEY = stringPreferencesKey("date")
    }

    suspend fun setDate(date: String = getCurrentDate()) {
        context.dateDataStore.edit {
            it[DATE_KEY] = date
        }
    }

    val dateFlow: Flow<String?> = context.dateDataStore.data.catch { exception ->
        if (exception is IOException) emit(emptyPreferences())
        else throw exception
    }.map {
        it[DATE_KEY] ?: ""
    }

    private fun getCurrentDate(): String {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

        return formatter.format(date)
    }
}