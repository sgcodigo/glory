package com.codigo.glory.domain.viewstate.product.catalogue

import com.codigo.glory.domain.model.Product

data class ProductCatalogueViewState(
    val favouriteFilterOn: Boolean = false,
    val newFilterOn: Boolean = false,
    val sortOption: SortOption = SortOption.LOW_TO_HIGH,
    val products: List<Product> = emptyList(),
    val favouriteError: Throwable? = null
)

enum class SortOption {
    LOW_TO_HIGH,
    HIGH_TO_LOW
}