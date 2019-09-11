package com.codigo.glory.domain.executor

import io.reactivex.Scheduler

interface BackgroundThread {
    fun getScheduler(): Scheduler
}
