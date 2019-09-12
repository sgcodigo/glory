package com.codigo.glory.app.feature.products

/**
 * Created by khunzohn on 2019-09-12 at Codigo
 */
sealed class ProductsEvent {
    data class UpdateFavouriteError(val error: Throwable) : ProductsEvent()
}