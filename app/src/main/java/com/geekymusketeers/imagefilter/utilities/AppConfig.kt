package com.geekymusketeers.imagefilter.utilities

import android.app.Application
import com.geekymusketeers.imagefilter.dependency.repositoryModule
import com.geekymusketeers.imagefilter.dependency.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppConfig : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}