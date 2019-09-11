package com.codigo.glory.data.datasource.cache

import io.reactivex.Observable

interface ProductCache<T> {

    fun stream(): Observable<List<T>>

    fun update(values: List<T>)

    fun toggleFavourite(value: T)

    fun reset()
}
