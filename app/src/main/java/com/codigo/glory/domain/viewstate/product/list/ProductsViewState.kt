package com.codigo.glory.domain.viewstate.product.list

import com.codigo.glory.domain.model.Product

data class ProductsViewState(
    val macs: List<Product> = emptyList(),
    val loadingMacs: Boolean = false,
    val loadMacsError: Throwable? = null,
    val iPhones: List<Product> = emptyList(),
    val loadingIPhones: Boolean = false,
    val loadIPhonesError: Throwable? = null,
    val favouriteError: Throwable? = null
)