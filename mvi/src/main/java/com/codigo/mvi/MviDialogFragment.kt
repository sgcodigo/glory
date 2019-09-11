package com.codigo.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class MviDialogFragment<VM : MviViewModel<VS, E, I>, VS, E, I> :
    DialogFragment() {

    protected var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)

        init(view)

        return view
    }

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

    /**
     * Override this to initialize view related stuffs
     */
    protected open fun init(view: View) {
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM

    abstract fun render(viewState: VS)

    abstract fun renderEvent(event: E)

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun renderEventInternal(event: E) {
        if (isDetached) return
        renderEvent(event)
    }

    private fun renderInternal(viewState: VS) {
        if (isDetached) return
        render(viewState)
    }
}
