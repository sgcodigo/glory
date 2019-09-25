package com.codigo.photo

import android.app.Application
import com.codigo.photo.app.di.networkModule
import com.codigo.photo.app.di.repositoryModule
import com.codigo.photo.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by heinhtet on 19,September,2019
 */
class SampleRxApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SampleRxApp)
            loadKoinModules(
                arrayListOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
