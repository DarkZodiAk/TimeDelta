package com.darkzodiak.timedelta

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.darkzodiak.timedelta.data.worker.DeltaWorkerFactory
import com.darkzodiak.timedelta.di.DeltaModule
import com.darkzodiak.timedelta.di.DeltaModuleImpl

class TimeDeltaApp: Application(), Configuration.Provider {

    companion object {
        lateinit var appModule: DeltaModule
    }

    override val workManagerConfiguration: Configuration = run {
        val deltaWorkerFactory = DelegatingWorkerFactory()

        deltaWorkerFactory.addFactory(DeltaWorkerFactory(appModule))

        Configuration.Builder()
            .setWorkerFactory(deltaWorkerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appModule = DeltaModuleImpl(applicationContext)
    }
}