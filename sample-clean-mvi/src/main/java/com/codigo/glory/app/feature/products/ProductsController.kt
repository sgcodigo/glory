package com.codigo.glory.app.feature.products

import android.content.Context
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.codigo.glory.R
import com.codigo.glory.app.epoxymodel.*
import com.codigo.glory.domain.model.Product
import com.codigo.glory.domain.viewstate.product.list.ProductsViewState
import io.reactivex.subjects.PublishSubject

class ProductsController constructor(
    private val context: Context
) : TypedEpoxyController<ProductsViewState>() {

    private val retryFetchMacsSubject = PublishSubject.create<Any>()
    private val retryFetchIPhonesSubject = PublishSubject.create<Any>()
    private val updateFavouriteSubject = PublishSubject.create<Product>()
    private val seeAllSubject = PublishSubject.create<Int>()

    fun retryFetchMacsClicks() = retryFetchMacsSubject

    fun retryFetchIPhonesClicks() = retryFetchIPhonesSubject

    fun updateFavouriteClicks() = updateFavouriteSubject

    fun seeAllClicks() = seeAllSubject

    override fun buildModels(data: ProductsViewState) {
        sectionTitle {
            id("Macs")
            seeAllAction {  }
            sectionTitle(context.getString(R.string.mac_section_title))
        }

        if (data.loadingMacs) {
            if (data.macs.isEmpty()) {
                loading {
                    id("Loading Macs")
                }
            } else {
                horizontalLoading {
                    id("Loading Macs")
                }
            }
        }

        if (data.loadMacsError != null) {
            if (data.macs.isEmpty()) {
                error {
                    id("Load Macs Error")
                    retryAction { retryFetchMacsSubject.onNext(Any()) }
                    errorMessage(data.loadMacsError.localizedMessage ?: "")
                }
            } else {
                horizontalError {
                    id("Load Macs Error")
                    retryListener { retryFetchMacsSubject.onNext(Any()) }
                    errorMessage(data.loadMacsError.localizedMessage ?: "")
                }
            }
        }

        if (data.macs.isNotEmpty()) {
            val carouselPadding = Carousel.Padding.dp(
                8, 8, 8,
                24, 4
            )
            val numViewsToShowOnScreen = 2.1f

            carousel {
                id("Mac Carousel")
                withModelsFrom(data.macs) { product ->
                    ProductModel_()
                        .id((product as Product.Mac).id)
                        .updateFavouriteAction { updateFavouriteSubject.onNext(it) }
                        .appleProduct(product)
                }
                numViewsToShowOnScreen(numViewsToShowOnScreen)
                padding(carouselPadding)
                hasFixedSize(true)
            }
        }

        sectionTitle {
            id("IPhones")
            seeAllAction {  }
            sectionTitle(context.getString(R.string.iphone_section_title))
        }

        if (data.loadingIPhones) {
            if (data.iPhones.isEmpty()) {
                loading {
                    id("Loading iPhones")
                }
            } else {
                horizontalLoading {
                    id("Loading iPhones")
                }
            }
        }
        if (data.loadIPhonesError != null) {
            if (data.iPhones.isEmpty()) {
                error {
                    id("Load iPhones Error")
                    retryAction { retryFetchIPhonesSubject.onNext(Any()) }
                    errorMessage(data.loadIPhonesError?.localizedMessage ?: "")
                }
            } else {
                horizontalError {
                    id("Load iPhones Error")
                    retryListener { retryFetchIPhonesSubject.onNext(Any()) }
                    errorMessage(data.loadIPhonesError?.localizedMessage ?: "")
                }
            }
        }

        if (data.iPhones.isNotEmpty()) {
            val carouselPadding = Carousel.Padding.dp(
                8, 8, 8,
                24, 4
            )
            val numViewsToShowOnScreen = 2.1f

            carousel {
                id("IPhones Carousel")
                withModelsFrom(data.iPhones) { product ->
                    ProductModel_()
                        .id((product as Product.IPhone).id)
                        .updateFavouriteAction { updateFavouriteSubject.onNext(it) }
                        .appleProduct(product)
                }
                numViewsToShowOnScreen(numViewsToShowOnScreen)
                padding(carouselPadding)
                hasFixedSize(true)
            }
        }
    }
}