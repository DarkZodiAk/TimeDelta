package com.darkzodiak.timedelta

import android.app.Application
import com.darkzodiak.timedelta.di.DeltaModule
import com.darkzodiak.timedelta.di.DeltaModuleImpl

class TimeDeltaApp: Application() {

    companion object {
        lateinit var appModule: DeltaModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = DeltaModuleImpl(applicationContext)
    }
}