package com.codigo.mvi.livedata

import androidx.lifecycle.*

abstract class MviViewModel<VS, E> : ViewModel() {

    protected val viewStateLiveData = MediatorLiveData<VS>()
    private val eventLiveData = SingleLiveEvent<E>()

    /**
     * ViewStates out put from the view Model
     */
    fun streamViewSates(): LiveData<VS> = viewStateLiveData

    /**
     * One time events out put from the view Model
     */
    fun streamEvents(): LiveData<E> = eventLiveData

    /**
     * Call inside ViewModel to emit one time event like error or navigation event
     */
    protected fun emitEvent(event: E) {
        eventLiveData.value = event
    }
}