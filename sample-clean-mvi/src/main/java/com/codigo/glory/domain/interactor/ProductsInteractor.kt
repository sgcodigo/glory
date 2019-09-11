package com.codigo.glory.domain.interactor

import com.codigo.glory.domain.executor.PostExecutionThread
import com.codigo.glory.domain.executor.BackgroundThread
import com.codigo.glory.domain.model.Product
import com.codigo.glory.domain.repository.ProductRepository
import com.codigo.glory.domain.viewstate.product.list.ProductsPartialState
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ProductsInteractor constructor(
    private val productRepository: ProductRepository,
    mainThread: PostExecutionThread,
    backgroundThread: BackgroundThread
) : BaseInteractor(mainThread, backgroundThread) {

    fun streamMacs(): Observable<ProductsPartialState> {
        return productRepository.streamMacs()
            .map { ProductsPartialState.MacsResult(it) }
            .cast(ProductsPartialState::class.java)
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun streamIPhones(): Observable<ProductsPartialState> {
        return productRepository.streamIPhones()
            .map { ProductsPartialState.IPhonesResult(it) }
            .cast(ProductsPartialState::class.java)
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun fetchMacs(): Observable<ProductsPartialState> {
        return productRepository.fetchMacs()
            .toObservable()
            .map { ProductsPartialState.MacsLoaded }
            .cast(ProductsPartialState::class.java)
            .startWith(ProductsPartialState.LoadingMacs)
            .onErrorReturn { ProductsPartialState.LoadMacsError(it) }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun fetchIPhones(): Observable<ProductsPartialState> {
        return productRepository.fetchIPhones()
            .toObservable()
            .map { ProductsPartialState.IPhonesLoaded }
            .cast(ProductsPartialState::class.java)
            .startWith(ProductsPartialState.LoadingIphones)
            .onErrorReturn { ProductsPartialState.LoadIPhonesError(it) }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun toggleFavourite(iPhone: Product.IPhone): Observable<ProductsPartialState> {
        return productRepository.toggleFavourite(iPhone)
            .toObservable()
            .map { ProductsPartialState.FavouriteUpdated }
            .cast(ProductsPartialState::class.java)
            .onErrorResumeNext { error: Throwable ->
                // Error can be dismissed at view side without being notified to interactor (toast, dialog,etc ...).
                // So have to simulate a reset mechanism to not show the error again on screen re-entering
                Observable.just(ProductsPartialState.UpdateFavouriteError(null)) //reset error after 200 millis
                    .delay(200, TimeUnit.MILLISECONDS)
                    .startWith(ProductsPartialState.UpdateFavouriteError(error)) //emit error
            }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun toggleFavourite(mac: Product.Mac): Observable<ProductsPartialState> {
        return productRepository.toggleFavourite(mac)
            .toObservable()
            .map { ProductsPartialState.FavouriteUpdated }
            .cast(ProductsPartialState::class.java)
            .onErrorResumeNext { error: Throwable ->
                // Error can be dismissed at view side without being notified to interactor (toast, dialog,etc ...).
                // So have to simulate a reset mechanism to not show the error again on screen re-entering
                Observable.just(ProductsPartialState.UpdateFavouriteError(null)) //reset error after 200 millis
                    .delay(200, TimeUnit.MILLISECONDS)
                    .startWith(ProductsPartialState.UpdateFavouriteError(error)) //emit error
            }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }
}