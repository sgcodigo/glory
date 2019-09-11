package com.codigo.glory.app.di

import com.codigo.glory.app.UiThread
import com.codigo.glory.data.executor.BackgroundThreadImpl
import com.codigo.glory.data.executor.JobsExecutor
import com.codigo.glory.domain.executor.BackgroundThread
import com.codigo.glory.domain.executor.PostExecutionThread
import org.koin.dsl.module

/**
 * Created by khunzohn on 2019-09-11 at Codigo
 */
val appModule = module {

    single { UiThread() as PostExecutionThread }

    single<BackgroundThread> { BackgroundThreadImpl(JobsExecutor()) }
}