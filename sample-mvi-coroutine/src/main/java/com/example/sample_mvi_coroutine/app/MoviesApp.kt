package com.example.sample_mvi_coroutine.app

import android.app.Application
import com.example.sample_mvi_coroutine.BuildConfig
import com.example.sample_mvi_coroutine.app.di.dataModule
import com.example.sample_mvi_coroutine.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber

class MoviesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@MoviesApp)
            loadKoinModules(
                listOf(
                    dataModule, viewModelModule
                )
            )
        }
    }
}