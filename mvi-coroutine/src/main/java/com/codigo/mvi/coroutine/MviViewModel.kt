package com.codigo.mvi.coroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

abstract class MviViewModel<VS, E>: ViewModel() {

    private val viewStateLiveData = MutableLiveData<VS>()
    private val eventLiveData = SingleLiveEvent<E>()

    private var subscribedAlready = false

    fun subscribeStates() {
        if (!subscribedAlready) {
            subscribedAlready = true
            processIntents().map { viewStateLiveData.value = it }
        }
    }

    /**
     * ViewStates out put from the view Model
     */
    fun streamViewSates(): LiveData<VS> = viewStateLiveData

    /**
     * One time events out put from the view Model
     */
    fun streamEvents(): LiveData<E> = eventLiveData

    protected abstract fun processIntents(): LiveData<VS>

    /**
     * Call inside ViewModel to emit one time event like error or navigation event
     */
    protected fun emitEvent(event: E) {
        eventLiveData.value = event
    }
}