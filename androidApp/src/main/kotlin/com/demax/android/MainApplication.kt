package com.demax.android

import android.app.Application
import com.demax.android.di.getAllKoinModules
import org.koin.android.ext.android.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.inject

class MainApplication : Application() {

    private val appInitializer: AppInitializer by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(getAllKoinModules())
        }
        appInitializer.initialize()
    }
}