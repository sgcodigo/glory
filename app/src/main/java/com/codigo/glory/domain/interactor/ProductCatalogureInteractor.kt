package com.codigo.glory.domain.interactor

import com.codigo.glory.domain.executor.BackgroundThread
import com.codigo.glory.domain.executor.PostExecutionThread
import com.codigo.glory.domain.model.Product
import com.codigo.glory.domain.repository.ProductRepository
import com.codigo.glory.domain.viewstate.product.catalogue.ProductCataloguePartialState
import com.codigo.glory.domain.viewstate.product.catalogue.SortOption
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ProductCatalogureInteractor constructor(
    private val productRepository: ProductRepository,
    mainThread: PostExecutionThread,
    backgroundThread: BackgroundThread
) : BaseInteractor(mainThread, backgroundThread) {

    private val newFilterSubject = BehaviorSubject.create<Boolean>()

    private val favouriteFilterSubject = BehaviorSubject.create<Boolean>()

    private val sortOptionSubject = BehaviorSubject.create<SortOption>()

    fun streamMacs(): Observable<ProductCataloguePartialState> {
        //combineLatest needs all streams to emit at least once
        newFilterSubject.onNext(false)
        favouriteFilterSubject.onNext(false)
        sortOptionSubject.onNext(SortOption.LOW_TO_HIGH)
        return Observable.combineLatest(
            listOf(
                productRepository.streamMacs(),
                newFilterSubject,
                favouriteFilterSubject,
                sortOptionSubject
            )
        ) {
            val (macs, newFilterOn, favouriteFilterOn, sortOption) = it
            applyFilterAndSortOptionForMacs(
                macs as List<Product.Mac>,
                newFilterOn as Boolean,
                favouriteFilterOn as Boolean,
                sortOption as SortOption
            )
        }
            .cast(ProductCataloguePartialState::class.java)
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun streamIPhones(): Observable<ProductCataloguePartialState> {
        //combineLatest needs all streams to emit at least once
        newFilterSubject.onNext(false)
        favouriteFilterSubject.onNext(false)
        sortOptionSubject.onNext(SortOption.LOW_TO_HIGH)
        return Observable.combineLatest(
            listOf(
                productRepository.streamIPhones(),
                newFilterSubject,
                favouriteFilterSubject,
                sortOptionSubject
            )
        ) {
            val (iPhones, newFilterOn, favouriteFilterOn, sortOption) = it
            applyFilterAndSortOptionForIPhones(
                iPhones as List<Product.IPhone>,
                newFilterOn as Boolean,
                favouriteFilterOn as Boolean,
                sortOption as SortOption
            )
        }
            .cast(ProductCataloguePartialState::class.java)
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun toggleNewFilter() {
        val currentFilter = newFilterSubject.value ?: false
        newFilterSubject.onNext(!currentFilter)
    }

    fun toggleFavouriteFilter() {
        val currentFilter = favouriteFilterSubject.value ?: false
        favouriteFilterSubject.onNext(!currentFilter)
    }

    fun updateSortOption(sortOption: SortOption) {
        sortOptionSubject.onNext(sortOption)
    }

    fun toggleFavourite(iPhone: Product.IPhone): Observable<ProductCataloguePartialState> {
        return productRepository.toggleFavourite(iPhone)
            .toObservable()
            .map { ProductCataloguePartialState.FavouriteUpdated }
            .cast(ProductCataloguePartialState::class.java)
            .onErrorResumeNext { error: Throwable ->
                // Error can be dismissed at view side without being notified to interactor (toast, dialog,etc ...).
                // So have to simulate a reset mechanism to not show the error again on screen re-entering
                Observable.just(ProductCataloguePartialState.FavouriteError(null)) //reset error after 200 millis
                    .delay(200, TimeUnit.MILLISECONDS)
                    .startWith(ProductCataloguePartialState.FavouriteError(error)) //emit error
            }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun toggleFavourite(mac: Product.Mac): Observable<ProductCataloguePartialState> {
        return productRepository.toggleFavourite(mac)
            .toObservable()
            .map { ProductCataloguePartialState.FavouriteUpdated }
            .cast(ProductCataloguePartialState::class.java)
            .onErrorResumeNext { error: Throwable ->
                // Error can be dismissed at view side without being notified to interactor (toast, dialog,etc ...).
                // So have to simulate a reset mechanism to not show the error again on screen re-entering
                Observable.just(ProductCataloguePartialState.FavouriteError(null)) //reset error after 200 millis
                    .delay(200, TimeUnit.MILLISECONDS)
                    .startWith(ProductCataloguePartialState.FavouriteError(error)) //emit error
            }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    private fun applyFilterAndSortOptionForMacs(
        list: List<Product.Mac>,
        newFilterOn: Boolean,
        favouriteFilterOn: Boolean,
        sortOption: SortOption
    ): ProductCataloguePartialState {
        return ProductCataloguePartialState.ProductResult(
            product = list
                .filter { if (favouriteFilterOn) it.favourite else true }
                .filter { if (newFilterOn) it.new else true }
                .let { macs ->
                    if (sortOption == SortOption.LOW_TO_HIGH) {
                        macs.sortedBy { it.price }
                    } else {
                        macs.sortedByDescending { it.price }
                    }
                },
            newFilterOn = newFilterOn,
            favouriteFilterOn = favouriteFilterOn,
            sortOption = sortOption
        )
    }

    private fun applyFilterAndSortOptionForIPhones(
        list: List<Product.IPhone>,
        newFilterOn: Boolean,
        favouriteFilterOn: Boolean,
        sortOption: SortOption
    ): ProductCataloguePartialState {
        return ProductCataloguePartialState.ProductResult(
            product = list
                .filter { if (favouriteFilterOn) it.favourite else true }
                .filter { if (newFilterOn) it.new else true }
                .let { iPhones ->
                    if (sortOption == SortOption.LOW_TO_HIGH) {
                        iPhones.sortedBy { it.price }
                    } else {
                        iPhones.sortedByDescending { it.price }
                    }
                },
            newFilterOn = newFilterOn,
            favouriteFilterOn = favouriteFilterOn,
            sortOption = sortOption
        )
    }
}