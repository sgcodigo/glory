package com.codigo.glory.app.feature.products

import com.codigo.glory.domain.interactor.ProductsInteractor
import com.codigo.glory.domain.model.Product
import com.codigo.glory.domain.viewstate.product.list.ProductsViewState
import com.codigo.mvi.rx.MviViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ProductsViewModel(
    private val interactor: ProductsInteractor
) : MviViewModel<ProductsViewState, Unit>() {

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
        val streamMacsStates = streamMacsSubject.switchMap { interactor.streamMacs() }
        val streamIPhonesStates = streamIPhonesSubject.switchMap { interactor.streamIPhones() }
        val fetchMacsStates = fetchMacsSubject.switchMap { interactor.fetchMacs() }
        val fetchIPhonesStates = fetchIPhonesSubject.switchMap { interactor.fetchIPhones() }
        val retryFetchMacsStates = retryFetchMacsSubject.switchMap { interactor.fetchMacs() }
        val retryFetchIPhonesStates =
            retryFetchIPhonesSubject.switchMap { interactor.fetchIPhones() }
        val toggleFavouriteStates = toggleFavouriteSubject.switchMap {
            if (it is Product.Mac) {
                interactor.toggleFavourite(it)

            } else {
                interactor.toggleFavourite(it as Product.IPhone)
            }
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