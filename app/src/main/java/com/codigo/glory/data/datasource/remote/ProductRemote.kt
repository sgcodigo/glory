package com.codigo.glory.data.datasource.remote

import com.codigo.glory.domain.model.Product
import io.reactivex.Single

interface ProductRemote {

    fun getMacs(): Single<List<Product.Mac>>

    fun favouriteMac(macId: String): Single<Any>

    fun unFavouriteMac(macId: String): Single<Any>

    fun getIPhones(): Single<List<Product.IPhone>>

    fun favouriteIPhone(iPhoneId: String): Single<Any>

    fun unFavouriteIPhone(iPhoneId: String): Single<Any>
}