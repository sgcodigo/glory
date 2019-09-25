package com.codigo.mvi.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class MviViewModel<VS, E, I> : ViewModel() {

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

    abstract fun sendIntent(intent: I)

    /**
     * Call inside ViewModel to emit one time event like error or navigation event
     */
    protected fun emitEvent(event: E) {
        viewModelScope.launch(Dispatchers.Main) {
            eventLiveData.value = event
        }
    }

    protected fun postEvent(event: E) {
        eventLiveData.postValue(event)
    }
}