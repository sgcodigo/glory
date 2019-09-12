package com.codigo.mvi.rx.rxutils

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by heinhtet on 12,September,2019
 */
object RxSchedulers {
    fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applySingleAsync(): SingleTransformer<T, T> {
        return SingleTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyObservableCompute(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyObservableMainThread(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.observeOn(
                AndroidSchedulers.mainThread()
            )
        }
    }

    fun <T> applyFlowableMainThread(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable ->
            flowable.observeOn(
                AndroidSchedulers.mainThread()
            )
        }
    }

    fun <T> applyFlowableAsysnc(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable ->
            flowable
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyFlowableCompute():
            FlowableTransformer<T, T> {
        return FlowableTransformer { flowable ->
            flowable.observeOn(AndroidSchedulers.mainThread())
        }
    }
}