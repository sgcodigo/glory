package com.deevvdd.sample_rx.app.features.home

import com.codigo.mvi.rx.MviViewModel
import com.codigo.mvi.rx.rxutils.RxSchedulers
import com.deevvdd.sample_rx.app.features.home.mvi.event.HomeEvent
import com.deevvdd.sample_rx.app.features.home.mvi.viewstate.HomePartialState
import com.deevvdd.sample_rx.app.features.home.mvi.viewstate.HomeViewState
import com.deevvdd.sample_rx.data.repository.MainRepositoryImpl
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by heinhtet on 19,September,2019
 */

class HomeViewModel(private val mainRepositoryImpl: MainRepositoryImpl) :
    MviViewModel<HomeViewState, HomeEvent>() {

    private val fetchPopularSubject = PublishSubject.create<Int>()


    fun fetchPopularPhoto(page: Int) {
        fetchPopularSubject.onNext(page)
    }

    override fun processIntents(): Observable<HomeViewState> {

        val fetchPopularPhoto = fetchPopularSubject.switchMap {
            mainRepositoryImpl
                .fetchPopularPhoto(it)
                .toObservable()
                .compose(RxSchedulers.applyObservableAsync())
                .map { HomePartialState.PopularPhotoResult(it) }
                .cast(HomePartialState::class.java)
                .startWith(HomePartialState.LoadingPopularPhoto)
                .onErrorReturn { HomePartialState.ErrorPopularPhoto(it) }
        }

        val state = Observable.mergeArray(
            fetchPopularPhoto
        )
        return state.scan(HomeViewState(), { oldState, partialState ->
            partialState.reduce(oldState)
        })
    }
}