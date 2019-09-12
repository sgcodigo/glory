package com.codigo.glory.domain.interactor

import com.codigo.glory.domain.executor.BackgroundThread
import com.codigo.glory.domain.executor.PostExecutionThread
import com.codigo.glory.domain.model.Product
import com.codigo.glory.domain.model.result.Result
import com.codigo.glory.domain.repository.ProductRepository
import com.codigo.glory.domain.type.Either
import io.reactivex.Observable

class ProductsInteractor constructor(
    private val productRepository: ProductRepository,
    mainThread: PostExecutionThread,
    backgroundThread: BackgroundThread
) : BaseInteractor(mainThread, backgroundThread) {

    fun streamMacs(): Observable<Result.Success<List<Product.Mac>>> {
        return productRepository.streamMacs()
            .map { Result.Success(it) }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun streamIPhones(): Observable<Result.Success<List<Product.IPhone>>> {
        return productRepository.streamIPhones()
            .map { Result.Success(it) }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun fetchMacs(): Observable<Result<List<Product.Mac>>> {
        return productRepository.fetchMacs()
            .toObservable()
            .map { Result.Success(it) as Result<List<Product.Mac>> }
            .startWith(Result.Loading)
            .onErrorReturn { Result.Error(it) }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun fetchIPhones(): Observable<Result<List<Product.IPhone>>> {
        return productRepository.fetchIPhones()
            .toObservable()
            .map { Result.Success(it) as Result<List<Product.IPhone>> }
            .startWith(Result.Loading)
            .onErrorReturn { Result.Error(it) }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun toggleFavourite(iPhone: Product.IPhone): Observable<Either<Throwable, Any>> {
        return productRepository.toggleFavourite(iPhone)
            .toObservable()
            .map { Either.Right(Any()) as Either<Throwable, Any> }
            .onErrorReturn { Either.Left(it) }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }

    fun toggleFavourite(mac: Product.Mac): Observable<Either<Throwable, Any>> {
        return productRepository.toggleFavourite(mac)
            .toObservable()
            .map { Either.Right(Any()) as Either<Throwable, Any> }
            .onErrorReturn { Either.Left(it) }
            .subscribeOn(backgroundThread.getScheduler())
            .observeOn(mainThread.getScheduler())
    }
}