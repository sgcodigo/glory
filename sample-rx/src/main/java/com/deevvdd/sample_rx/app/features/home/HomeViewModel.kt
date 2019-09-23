package com.deevvdd.sample_rx.app.features.home

import com.codigo.mvi.rx.MviViewModel
import com.codigo.mvi.rx.rxutils.RxSchedulers
import com.deevvdd.sample_rx.app.features.home.mvi.event.HomeEvent
import com.deevvdd.sample_rx.app.features.home.mvi.viewstate.HomePartialState
import com.deevvdd.sample_rx.app.features.home.mvi.viewstate.HomeViewState
import com.deevvdd.sample_rx.app.utils.POPULAR
import com.deevvdd.sample_rx.data.model.request.PhotoRequest
import com.deevvdd.sample_rx.data.repository.MainRepositoryImpl
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * Created by heinhtet on 19,September,2019
 */

class HomeViewModel(
    private val mainRepositoryImpl: MainRepositoryImpl
) :
    MviViewModel<HomeViewState, HomeEvent>() {

    var orderBy = POPULAR
    var page = 1
    var selectedCategoryName = "travel"

    private val fetchPhotoSubject = PublishSubject.create<PhotoRequest>()
    private val fetchInitialPhotoSubject = PublishSubject.create<PhotoRequest>()
    private val fetchPhotoLoadMoreSubject = PublishSubject.create<PhotoRequest>()


    fun fetchPhoto(request: PhotoRequest) {
        Timber.d("fetchMorePhoto $request")
        fetchPhotoSubject.onNext(request)
    }

    fun fetchMorePhoto(request: PhotoRequest) {
        Timber.d("fetchMorePhoto $request")
        fetchPhotoLoadMoreSubject.onNext(request)
    }

    fun fetchInitialPhoto(request: PhotoRequest) {
        Timber.d("fetchInitialPhoto $request")
        fetchInitialPhotoSubject.onNext(request)
    }


    private fun startWithValue(page: Int): HomePartialState {
        return if (page > 1) {
            HomePartialState.ViewMoreLoadingPhoto
        } else {
            HomePartialState.LoadingPhoto
        }
    }


    override fun processIntents(): Observable<HomeViewState> {

        val fetchInitialPhotoState =
            fetchInitialPhotoSubject
                .take(1)
                .switchMap { request ->
                    mainRepositoryImpl
                        .fetchPopularPhoto(request)
                        .toObservable()
                        .compose(RxSchedulers.applyObservableAsync())
                        .map { HomePartialState.PhotoResult(it) }
                        .cast(HomePartialState::class.java)
                        .startWith(HomePartialState.LoadingPhoto)
                        .onErrorReturn { HomePartialState.ErrorPhoto(it) }
                }


        val fetchPhotoState =
            fetchPhotoSubject
                .switchMap { request ->
                    mainRepositoryImpl
                        .fetchPopularPhoto(request)
                        .toObservable()
                        .compose(RxSchedulers.applyObservableAsync())
                        .map { HomePartialState.PhotoResult(it) }
                        .cast(HomePartialState::class.java)
                        .startWith(HomePartialState.LoadingPhoto)
                        .onErrorReturn { HomePartialState.ErrorPhoto(it) }
                }


        val fetchLoadMorePhotoState =
            fetchPhotoLoadMoreSubject
                .switchMap { request ->
                    mainRepositoryImpl
                        .fetchPopularPhoto(request)
                        .toObservable()
                        .compose(RxSchedulers.applyObservableAsync())
                        .map { HomePartialState.PhotoResult(it) }
                        .cast(HomePartialState::class.java)
                        .startWith(startWithValue(request.page))
                        .onErrorReturn { HomePartialState.ErrorPhoto(it) }
                }

        val state = Observable.mergeArray(
            fetchInitialPhotoState,
            fetchPhotoState,
            fetchLoadMorePhotoState
        ).distinctUntilChanged()
        return state.scan(HomeViewState(), { oldState, partialState ->
            partialState.reduce(oldState)
        })
    }


    private fun filterSubject(request: PhotoRequest) {

    }
}