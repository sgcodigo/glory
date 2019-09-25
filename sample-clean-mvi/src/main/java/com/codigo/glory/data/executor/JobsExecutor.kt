package com.codigo.glory.data.executor

import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory

class JobsExecutor : Executor {
    private val initialPoolSize = 3
    private val maxPoolSize = 5

    // Sets the amount of time an idle thread waits before terminating
    private val keepAliveTime = 10

    // Sets the Time Unit to seconds
    private val keepAliveTimeUnit = TimeUnit.SECONDS

    private val workQueue: BlockingQueue<Runnable>
    private val threadPoolExecutor: ThreadPoolExecutor
    private val threadFactory: ThreadFactory

    init {
        workQueue = LinkedBlockingDeque()
        threadFactory = JobThreadFactory()
        threadPoolExecutor = ThreadPoolExecutor(
            initialPoolSize, maxPoolSize, keepAliveTime.toLong(),
            keepAliveTimeUnit, workQueue, threadFactory
        )
    }

    override fun execute(p0: Runnable?) {
        if (p0 == null) {
            throw IllegalArgumentException("Runnable to saveLanguage cannot be null")
        }
        threadPoolExecutor.execute(p0)
    }

    private class JobThreadFactory : ThreadFactory {
        private val threadName = "android_"
        private var counter = 0
        override fun newThread(p0: Runnable?): Thread = Thread(p0, threadName + counter++)
    }
}
