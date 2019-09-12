package com.codigo.glory.app.feature.products

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codigo.glory.R
import com.codigo.glory.domain.viewstate.product.list.ProductsViewState
import com.codigo.mvi.rx.MviActivity
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_products.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProductsActivity : MviActivity<ProductsViewModel, ProductsViewState, ProductsEvent>() {

    private val productsViewModel: ProductsViewModel by viewModel()

    private lateinit var controller: ProductsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller.updateFavouriteClicks()
            .subscribe { getViewModel().toggleFavourite(it) }
            .addTo(compositeDisposable)

        controller.retryFetchIPhonesClicks()
            .subscribe { getViewModel().retryFetchIPhones() }
            .addTo(compositeDisposable)

        controller.retryFetchMacsClicks()
            .subscribe { getViewModel().retryFetchMacs() }
            ?.addTo(compositeDisposable)

        getViewModel().streamMacs()
        getViewModel().streamIPhones()
        getViewModel().fetchIPhones()
        getViewModel().fetchMacs()
    }

    override fun setUpLayout() {
        setContentView(R.layout.activity_products)

        controller = ProductsController(this).apply {
            rvContent.setController(this)
            rvContent.layoutManager = LinearLayoutManager(this@ProductsActivity)
        }
    }

    override fun getViewModel(): ProductsViewModel = productsViewModel

    override fun renderEvent(event: ProductsEvent) {
        if (event is ProductsEvent.UpdateFavouriteError) {
            Toast.makeText(this, event.error.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun render(viewState: ProductsViewState) {
        controller.setData(viewState)
    }
}