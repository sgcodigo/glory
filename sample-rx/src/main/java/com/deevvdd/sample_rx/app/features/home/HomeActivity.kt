package com.deevvdd.sample_rx.app.features.home

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.codigo.mvi.rx.MviActivity
import com.deevvdd.sample_rx.R
import com.deevvdd.sample_rx.app.features.home.mvi.event.HomeEvent
import com.deevvdd.sample_rx.app.features.home.mvi.viewstate.HomeViewState
import com.deevvdd.sample_rx.app.features.home.widgets.PhotoAdapter
import com.deevvdd.sample_rx.app.utils.*
import com.deevvdd.sample_rx.data.model.request.PhotoRequest
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


/**
 * Created by heinhtet on 19,September,2019
 */
class HomeActivity : MviActivity<HomeViewModel, HomeViewState, HomeEvent>(), ErrorListener {


    override fun retry() {
        fetchPhoto()
    }

    private lateinit var photoAdapter: PhotoAdapter
    override fun setUpLayout() {
        setContentView(R.layout.activity_home)
        vgState.errorListener = this
        initRv()
        btnMore.setOnClickListener {
            homeViewModel.page++
            fetchPhoto()
        }
        initSpinner()
    }

    private fun initRv() {
        photoAdapter = PhotoAdapter()
        rvPhoto.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }


    val homeViewModel: HomeViewModel by viewModel()

    override fun getViewModel(): HomeViewModel {
        return homeViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.fetchInitialPhoto(createPhotoRequest(false))
    }

    override fun render(viewState: HomeViewState) {

        if (viewState.loading) {
            vgState.setState(State.Loading)
        } else {
            vgState.setState(State.Hide)
        }

        if (viewState.error != null) {
            vgState.setState(State.Error, viewState.error.localizedMessage, true)
        }

        if (viewState.viewMoreLoading) {
            pgLoading.show()
            btnMore.isEnabled = false
        } else {
            btnMore.isEnabled = true
            pgLoading.hide()
        }

        if (viewState.popularPhotoResult != null) {
            photoAdapter.setPhoto(viewState.popularPhotoResult.hits, homeViewModel.page)
        }

    }


    override fun renderEvent(event: HomeEvent) {
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter(this, R.layout.item_spinner, ORDER)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        spOrder.post {
            spOrder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                    if (p1 != null) {
                        homeViewModel.page = 1
                        homeViewModel.orderBy = p0.getItemAtPosition(p2).toString()
                        fetchPhoto()
                    }
                }
            }
        }
        spOrder.adapter = adapter
        val categoryAdapter = ArrayAdapter(this, R.layout.item_spinner, CATEGORIES)
        categoryAdapter.setDropDownViewResource(R.layout.item_spinner)
        spCategories.post {
            spCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                    if (p1 != null) {
                        homeViewModel.page = 1
                        homeViewModel.selectedCategoryName = p0.getItemAtPosition(p2).toString()
                        fetchPhoto()
                    }
                }
            }

        }
        spCategories.adapter = categoryAdapter
    }

    private fun fetchPhoto() {
        if (homeViewModel.page > 1) {
            homeViewModel.fetchMorePhoto(createPhotoRequest())
        } else {
            homeViewModel.fetchPhoto(createPhotoRequest())
        }
    }

    private fun createPhotoRequest(isRefreshedAll :Boolean = true): PhotoRequest {
        return PhotoRequest(
            homeViewModel.page,
            homeViewModel.selectedCategoryName,
            homeViewModel.orderBy,
            isRefreshedAll
        )
    }
}
