package com.codigo.glory.domain.viewstate.product.catalogue

import com.codigo.glory.domain.model.Product

sealed class ProductCataloguePartialState {

    abstract fun reduce(oldState: ProductCatalogueViewState): ProductCatalogueViewState

    data class ProductResult(
        val favouriteFilterOn: Boolean,
        val newFilterOn: Boolean,
        val sortOption: SortOption,
        val product: List<Product>
    ) : ProductCataloguePartialState() {
        override fun reduce(oldState: ProductCatalogueViewState): ProductCatalogueViewState {
            return oldState.copy(
                favouriteFilterOn = favouriteFilterOn,
                newFilterOn = newFilterOn,
                sortOption = sortOption,
                products = product
            )
        }
    }

    object FavouriteUpdated : ProductCataloguePartialState() {
        override fun reduce(oldState: ProductCatalogueViewState): ProductCatalogueViewState {
            return oldState.copy(
                favouriteError = null // reset error if any
            )
        }
    }

    data class FavouriteError(
        val error: Throwable? // nullable for toast, dialog etc...
    ) : ProductCataloguePartialState() {
        override fun reduce(oldState: ProductCatalogueViewState): ProductCatalogueViewState {
            return oldState.copy(
                favouriteError = error
            )
        }
    }
}