package com.codigo.glory.app.feature.products

import com.codigo.glory.domain.interactor.ProductsInteractor
import com.codigo.glory.domain.model.Product
import com.codigo.glory.domain.model.result.Result
import com.codigo.glory.domain.type.Either
import com.codigo.glory.domain.viewstate.product.list.ProductsPartialState
import com.codigo.glory.domain.viewstate.product.list.ProductsViewState
import com.codigo.mvi.rx.MviViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ProductsViewModel(
    private val interactor: ProductsInteractor
) : MviViewModel<ProductsViewState, ProductsEvent>() {

    private val streamMacsSubject = PublishSubject.create<Any>()
    private val streamIPhonesSubject = PublishSubject.create<Any>()
    private val fetchMacsSubject = PublishSubject.create<Any>()
    private val fetchIPhonesSubject = PublishSubject.create<Any>()
    private val retryFetchMacsSubject = PublishSubject.create<Any>()
    private val retryFetchIPhonesSubject = PublishSubject.create<Any>()
    private val toggleFavouriteSubject = PublishSubject.create<Product>()

    fun fetchMacs() {
        fetchMacsSubject.onNext(Any())
    }

    fun fetchIPhones() {
        fetchIPhonesSubject.onNext(Any())
    }

    fun retryFetchMacs() {
        retryFetchMacsSubject.onNext(Any())
    }

    fun retryFetchIPhones() {
        retryFetchIPhonesSubject.onNext(Any())
    }

    fun streamMacs() {
        streamMacsSubject.onNext(Any())
    }

    fun streamIPhones() {
        streamIPhonesSubject.onNext(Any())
    }

    fun toggleFavourite(product: Product) {
        toggleFavouriteSubject.onNext(product)
    }

    override fun processIntents(): Observable<ProductsViewState> {
        val streamMacsStates = streamMacsSubject.take(1)
            .switchMap { interactor.streamMacs() }
            .map { ProductsPartialState.MacsResult(it.data) as ProductsPartialState }

        val streamIPhonesStates = streamIPhonesSubject.take(1)
            .switchMap { interactor.streamIPhones() }
            .map { ProductsPartialState.IPhonesResult(it.data) }

        val fetchMacsStates = fetchMacsSubject.take(1)
            .switchMap { interactor.fetchMacs() }
            .map {
                when (it) {
                    is Result.Success -> {
                        ProductsPartialState.MacsLoaded
                    }
                    is Result.Loading -> {
                        ProductsPartialState.LoadingMacs
                    }
                    is Result.Error -> {
                        ProductsPartialState.LoadMacsError(it.exception)
                    }
                }
            }

        val fetchIPhonesStates = fetchIPhonesSubject.take(1)
            .switchMap { interactor.fetchIPhones() }
            .map {
                when (it) {
                    is Result.Success -> {
                        ProductsPartialState.IPhonesLoaded
                    }
                    is Result.Loading -> {
                        ProductsPartialState.LoadingIphones
                    }
                    is Result.Error -> {
                        ProductsPartialState.LoadIPhonesError(it.exception)
                    }
                }
            }
        val retryFetchMacsStates = retryFetchMacsSubject
            .switchMap { interactor.fetchMacs() }
            .map {
                when (it) {
                    is Result.Success -> {
                        ProductsPartialState.MacsLoaded
                    }
                    is Result.Loading -> {
                        ProductsPartialState.LoadingMacs
                    }
                    is Result.Error -> {
                        ProductsPartialState.LoadMacsError(it.exception)
                    }
                }
            }
        val retryFetchIPhonesStates = retryFetchIPhonesSubject
            .switchMap { interactor.fetchIPhones() }
            .map {
                when (it) {
                    is Result.Success -> {
                        ProductsPartialState.IPhonesLoaded
                    }
                    is Result.Loading -> {
                        ProductsPartialState.LoadingIphones
                    }
                    is Result.Error -> {
                        ProductsPartialState.LoadIPhonesError(it.exception)
                    }
                }
            }
        val toggleFavouriteStates = toggleFavouriteSubject.switchMap {
            if (it is Product.Mac) {
                interactor.toggleFavourite(it)
            } else {
                interactor.toggleFavourite(it as Product.IPhone)
            }
        }
            .map {
                if (it is Either.Left) {
                    emitEvent(ProductsEvent.UpdateFavouriteError(it.a))
                }
                ProductsPartialState.FavouriteUpdated
            }

        return Observable.mergeArray(
            streamMacsStates,
            streamIPhonesStates,
            fetchMacsStates,
            fetchIPhonesStates,
            retryFetchMacsStates,
            retryFetchIPhonesStates,
            toggleFavouriteStates
        )
            .scan(ProductsViewState()) { oldState, partialState ->
                partialState.reduce(oldState)
            }
            .distinctUntilChanged()
    }
}