package com.henry.nexus

import android.app.Application

class MainApplication : Application() {
    companion object {
        lateinit var INSTANCE: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
} 