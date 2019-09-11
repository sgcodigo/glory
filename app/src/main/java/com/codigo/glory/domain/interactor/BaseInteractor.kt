package com.codigo.glory.domain.interactor

import com.codigo.glory.domain.executor.PostExecutionThread
import com.codigo.glory.domain.executor.BackgroundThread

open class BaseInteractor(
    val mainThread: PostExecutionThread,
    val backgroundThread: BackgroundThread
)
