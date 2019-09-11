package com.codigo.glory.app

import com.codigo.glory.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class UiThread constructor() : PostExecutionThread {

    override fun getScheduler(): Scheduler = AndroidSchedulers.mainThread()
}
