package com.codigo.glory.app.feature.products

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codigo.glory.R
import com.codigo.glory.domain.viewstate.product.list.ProductsViewState
import com.codigo.mvi.rx.MviActivity
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_products.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProductsActivity : MviActivity<ProductsViewModel, ProductsViewState, Unit>() {

    private val productsViewModel: ProductsViewModel by viewModel()

    private var controller: ProductsController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = ProductsController(this).apply {
            rvContent.setController(this)
            rvContent.layoutManager = LinearLayoutManager(this@ProductsActivity)
        }


        controller?.updateFavouriteClicks()
            ?.subscribe { getViewModel().toggleFavourite(it) }
            ?.addTo(compositeDisposable)

        getViewModel().streamMacs()
        getViewModel().streamIPhones()
        getViewModel().fetchIPhones()
        getViewModel().fetchMacs()
    }

    override fun getLayoutId(): Int = R.layout.activity_products

    override fun getViewModel(): ProductsViewModel = productsViewModel

    override fun renderEvent(event: Unit) {
        // nothing to render
    }

    override fun render(viewState: ProductsViewState) {
        controller?.setData(viewState)
    }
}