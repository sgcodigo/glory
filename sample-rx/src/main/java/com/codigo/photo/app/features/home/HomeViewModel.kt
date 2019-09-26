package com.codigo.photo.app.features.home

import android.content.Context
import com.codigo.mvi.rx.MviViewModel
import com.codigo.mvi.rx.rxutils.RxSchedulers
import com.codigo.photo.app.features.home.mvi.event.HomeEvent
import com.codigo.photo.app.features.home.mvi.viewstate.HomePartialState
import com.codigo.photo.app.features.home.mvi.viewstate.HomeViewState
import com.codigo.photo.app.utils.POPULAR
import com.codigo.photo.data.model.request.PhotoRequest
import com.codigo.photo.data.network.hasNetwork
import com.codigo.photo.data.repository.MainRepositoryImpl
import com.deevvdd.sample_rx.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by heinhtet on 19,September,2019
 */

class HomeViewModel(
    private val context: Context,
    private val mainRepositoryImpl: MainRepositoryImpl
) :
    MviViewModel<HomeViewState, HomeEvent>() {

    var orderBy = POPULAR
    var page = 1
    var selectedCategoryName = "travel"

    private val fetchPhotoSubject = PublishSubject.create<PhotoRequest>()
    private val fetchInitialPhotoSubject = PublishSubject.create<PhotoRequest>()
    private val fetchPhotoRefreshSubject = PublishSubject.create<PhotoRequest>()
    private val fetchPhotoLoadMoreSubject = PublishSubject.create<PhotoRequest>()

    fun fetchPhoto(request: PhotoRequest) {
        fetchPhotoSubject.onNext(request)
    }

    fun fetchMorePhoto(request: PhotoRequest) {
        fetchPhotoLoadMoreSubject.onNext(request)
    }

    fun fetchInitialPhoto(request: PhotoRequest) {
        if (!hasNetwork(context)) {
            emitEvent(
                HomeEvent.NetworkError(
                    Throwable(context.resources.getString(R.string.message_network_error))
                )
            )
        } else {
            fetchInitialPhotoSubject.onNext(request)
        }
    }

    fun fetchPhotoRefresh(request: PhotoRequest) {
        fetchPhotoRefreshSubject.onNext(request)
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
                        .map { HomePartialState.PhotoResult(it, page) }
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
                        .map { HomePartialState.PhotoResult(it, page) }
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
                        .map { HomePartialState.PhotoResult(it, page) }
                        .cast(HomePartialState::class.java)
                        .startWith(startWithValue(request.page))
                        .onErrorReturn { HomePartialState.ErrorPhoto(it) }
                }

        val fetchRefreshPhotoState =
            fetchPhotoRefreshSubject
                .switchMap { request ->
                    mainRepositoryImpl
                        .fetchPopularPhoto(request)
                        .toObservable()
                        .compose(RxSchedulers.applyObservableAsync())
                        .map { HomePartialState.PhotoResult(it, page) }
                        .cast(HomePartialState::class.java)
                        .startWith(HomePartialState.LoadingPhoto)
                        .onErrorReturn { HomePartialState.ErrorPhoto(it) }
                }

        val state = Observable.mergeArray(
            fetchInitialPhotoState,
            fetchPhotoState,
            fetchLoadMorePhotoState,
            fetchRefreshPhotoState
        ).distinctUntilChanged()
        return state.scan(HomeViewState(), { oldState, partialState ->
            partialState.reduce(oldState)
        })
    }
}
