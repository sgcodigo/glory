package com.codigo.mvi

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class MviFragment<VM : MviViewModel<VS, E, I>, VS, E, I> : Fragment() {

    protected var compositeDisposable = CompositeDisposable()

    abstract fun getViewModel(): VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.clear()

        getViewModel().streamViewSates()
            .subscribe { renderInternal(it) }
            .addTo(compositeDisposable)

        getViewModel().streamEvents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { renderEventInternal(it) }
            .addTo(compositeDisposable)
    }

    abstract fun renderEvent(event: E)

    abstract fun render(viewState: VS)

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun renderInternal(viewState: VS) {
        if (isDetached) return
        render(viewState)
    }

    private fun renderEventInternal(event: E) {
        if (isDetached) return
        renderEvent(event)
    }
}
