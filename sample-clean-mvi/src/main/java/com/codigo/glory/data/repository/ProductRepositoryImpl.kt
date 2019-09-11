package com.codigo.glory.data.repository

import com.codigo.glory.data.datasource.cache.IPhoneCache
import com.codigo.glory.data.datasource.cache.MacCache
import com.codigo.glory.data.datasource.remote.ProductRemote
import com.codigo.glory.domain.model.Product
import com.codigo.glory.domain.repository.ProductRepository
import io.reactivex.Observable
import io.reactivex.Single

class ProductRepositoryImpl constructor(
    private val productRemote: ProductRemote,
    private val iPhoneCache: IPhoneCache,
    private val macCache: MacCache
) : ProductRepository {

    override fun fetchMacs(): Single<List<Product.Mac>> {
        return productRemote.getMacs()
            .doOnSuccess { macCache.update(it) }
    }

    override fun streamMacs(): Observable<List<Product.Mac>> {
        return macCache.stream()
    }

    override fun toggleFavourite(mac: Product.Mac): Single<Any> {
        val favouriteResult = if (mac.favourite) {
            productRemote.unFavouriteMac(mac.id)
        } else {
            productRemote.favouriteMac(mac.id)
        }
        return favouriteResult.doOnSuccess {
            macCache.toggleFavourite(mac)
        }
    }

    override fun fetchIPhones(): Single<List<Product.IPhone>> {
        return productRemote.getIPhones()
            .doOnSuccess { iPhoneCache.update(it) }
    }

    override fun streamIPhones(): Observable<List<Product.IPhone>> {
        return iPhoneCache.stream()
    }

    override fun toggleFavourite(iPhone: Product.IPhone): Single<Any> {
        val favouriteResult = if (iPhone.favourite) {
            productRemote.unFavouriteIPhone(iPhone.id)
        } else {
            productRemote.favouriteIPhone(iPhone.id)
        }
        return favouriteResult.doOnSuccess {
            iPhoneCache.toggleFavourite(iPhone)
        }
    }
}