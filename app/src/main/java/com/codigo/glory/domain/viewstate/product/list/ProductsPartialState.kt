package com.codigo.glory.domain.viewstate.product.list

import com.codigo.glory.domain.model.Product

sealed class ProductsPartialState {

    abstract fun reduce(oldState: ProductsViewState): ProductsViewState

    data class MacsResult(val macs: List<Product.Mac>) : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                macs = macs
            )
        }
    }

    object MacsLoaded : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                loadMacsError = null,
                loadingMacs = false
            )
        }
    }

    object LoadingMacs : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                loadingMacs = true,
                loadMacsError = null
            )
        }
    }

    data class LoadMacsError(val error: Throwable) : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                loadingMacs = false,
                loadMacsError = error
            )
        }
    }

    data class IPhonesResult(val iPhones: List<Product.IPhone>) : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                iPhones = iPhones
            )
        }
    }

    object LoadingIphones : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                loadingIPhones = true,
                loadIPhonesError = null
            )
        }
    }

    object IPhonesLoaded : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                loadIPhonesError = null,
                loadingIPhones = false
            )
        }
    }

    data class LoadIPhonesError(val error: Throwable) : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                loadingIPhones = false,
                loadIPhonesError = error
            )
        }
    }

    object FavouriteUpdated : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                favouriteError = null // re setting error just in case there's any
            )
        }
    }

    data class UpdateFavouriteError(
        val error: Throwable? // Yes, nullable for one time event, toast for example.
    ) : ProductsPartialState() {
        override fun reduce(oldState: ProductsViewState): ProductsViewState {
            return oldState.copy(
                favouriteError = error
            )
        }
    }
}