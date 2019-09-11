package com.codigo.mvi.rx

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class MviViewModel<VS, E> : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val viewStateSubject = BehaviorSubject.create<VS>()

    private val eventSubject = PublishSubject.create<E>()

    private var subscribedAlready = false

    fun subscribeStates() {
        if (!subscribedAlready) {
            subscribedAlready = true
            processIntents()
                .subscribe { viewStateSubject.onNext(it) }
                .addTo(compositeDisposable)
        }
    }

    /**
     * ViewStates out put from the view Model
     */
    fun streamViewSates(): Observable<VS> = viewStateSubject

    /**
     * One time events out put from the view Model
     */
    fun streamEvents(): Observable<E> = eventSubject

    protected abstract fun processIntents(): Observable<VS>

    /**
     * Call inside ViewModel to emit one time event like error or navigation event
     */
    protected fun emitEvent(event: E) {
        eventSubject.onNext(event)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
