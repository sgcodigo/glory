package com.codigo.glory.domain.repository

import com.codigo.glory.domain.model.Product
import io.reactivex.Observable
import io.reactivex.Single

interface ProductRepository {

    fun fetchMacs(): Single<List<Product.Mac>>

    fun streamMacs(): Observable<List<Product.Mac>>

    fun toggleFavourite(mac: Product.Mac): Single<Any>

    fun fetchIPhones(): Single<List<Product.IPhone>>

    fun streamIPhones(): Observable<List<Product.IPhone>>

    fun toggleFavourite(iPhone: Product.IPhone): Single<Any>
}