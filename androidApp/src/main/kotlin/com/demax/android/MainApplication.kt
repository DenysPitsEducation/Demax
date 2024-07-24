package com.demax.android

import android.app.Application
import com.demax.android.di.getAllKoinModules
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            modules(getAllKoinModules())
        }
    }
}