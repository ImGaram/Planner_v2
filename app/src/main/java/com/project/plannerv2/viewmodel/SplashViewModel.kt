package com.project.plannerv2.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.project.plannerv2.application.PlannerV2Application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SplashViewModel: ViewModel() {
    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState = _loginState.asStateFlow()

    private val _splashSate = MutableStateFlow(true)
    val splashState = _splashSate.asStateFlow()

    fun checkLogin() {
        viewModelScope.launch {
            val auth = FirebaseAuth.getInstance()
            _loginState.value = auth.currentUser != null
            if (_loginState.value != null) _splashSate.value = false
        }
    }

    suspend fun initDataStore(initDataStoreState: MutableState<Boolean>) {
        val dataStore = PlannerV2Application.getInstance().getDataStore()
        dataStore.setDate()

        dataStore.dateFlow.collect {
            if (it == getCurrentDate()) initDataStoreState.value = true
        }
    }

    private fun getCurrentDate(): String {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        return formatter.format(date)
    }
}