package com.codigo.movies.app

import android.app.Application
import com.codigo.movies.BuildConfig
import com.codigo.movies.app.di.dataModule
import com.codigo.movies.app.di.dbModule
import com.codigo.movies.app.di.useCaseModule
import com.codigo.movies.app.di.viewModelModule
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
                    dataModule, viewModelModule, dbModule, useCaseModule
                )
            )
        }
    }
}