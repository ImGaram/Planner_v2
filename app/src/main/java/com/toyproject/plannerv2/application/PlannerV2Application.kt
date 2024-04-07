package com.toyproject.plannerv2.application

import android.app.Application
import com.toyproject.plannerv2.util.DateDataStore

class PlannerV2Application: Application() {
    private lateinit var dataStore: DateDataStore

    companion object {
        private lateinit var plannerV2Application: PlannerV2Application
        fun getInstance() = plannerV2Application
    }

    override fun onCreate() {
        super.onCreate()

        plannerV2Application = this
        dataStore = DateDataStore(this)
    }

    fun getDataStore(): DateDataStore = dataStore
}