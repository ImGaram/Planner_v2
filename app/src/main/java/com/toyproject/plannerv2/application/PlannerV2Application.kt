package com.toyproject.plannerv2.application

import android.app.Application

class PlannerV2Application: Application() {
    companion object {
        private lateinit var plannerV2Application: PlannerV2Application
        fun getInstance() = plannerV2Application
    }

    override fun onCreate() {
        super.onCreate()

        plannerV2Application = this
    }
}