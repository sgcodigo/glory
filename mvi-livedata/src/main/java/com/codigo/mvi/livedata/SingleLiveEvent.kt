package com.codigo.mvi.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicBoolean
/**
 * Created by heinhtet on 30,July,2019
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val observers = CopyOnWriteArraySet<ObserverWrapper<in T>>()
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }
    override fun removeObservers(owner: LifecycleOwner) {
        observers.clear()
        super.removeObservers(owner)
    }
    override fun removeObserver(observer: Observer<in T>) {
        observers.remove(observer as Observer<*>)
        super.removeObserver(observer)
    }
    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.newValue() }
        super.setValue(t)
    }
    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
    private class ObserverWrapper<T>(private val observer: Observer<T>) : Observer<T> {
        private val pending = AtomicBoolean(false)
        override fun onChanged(t: T?) {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
        fun newValue() {
            pending.set(true)
        }
    }
}