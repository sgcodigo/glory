package com.codigo.photo.app.features.home

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codigo.mvi.rx.MviActivity
import com.codigo.photo.app.features.home.mvi.event.HomeEvent
import com.codigo.photo.app.features.home.mvi.viewstate.HomeViewState
import com.codigo.photo.app.features.home.widgets.OnSelectSortOptionListener
import com.codigo.photo.app.features.home.widgets.PhotoAdapter
import com.codigo.photo.app.features.home.widgets.SortSpinnerAdapter
import com.codigo.photo.app.utils.CATEGORIES
import com.codigo.photo.app.utils.ErrorListener
import com.codigo.photo.app.utils.ORDER
import com.codigo.photo.app.utils.State
import com.codigo.photo.data.model.SortOption
import com.codigo.photo.data.model.request.PhotoRequest
import com.deevvdd.sample_rx.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by heinhtet on 19,September,2019
 */
class HomeActivity : MviActivity<HomeViewModel, HomeViewState, HomeEvent>(), ErrorListener,
    OnSelectSortOptionListener {

    override fun retry() {
        fetchPhoto()
    }

    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var sortSpinnerAdapter: SortSpinnerAdapter

    override fun setUpLayout() {
        setContentView(R.layout.activity_home)
        vgState.errorListener = this
        initRv()
        initSpinner()
        initToolbar()
    }

    private fun initRv() {
        photoAdapter = PhotoAdapter()
        rvPhoto.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.page = 1
            homeViewModel.fetchPhotoRefresh(createPhotoRequest(false))
        }

        rvPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    homeViewModel.page++
                    fetchPhoto()
                }
            }
        })
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
            if (swipeRefreshLayout.isRefreshing)
                swipeRefreshLayout.isRefreshing = false
            vgState.setState(State.Error, viewState.error.localizedMessage, true)
        }

        if (viewState.popularPhotoResult != null) {
            if (swipeRefreshLayout.isRefreshing)
                swipeRefreshLayout.isRefreshing = false
            photoAdapter.setPhoto(viewState.popularPhotoResult.hits, homeViewModel.page)
        }
    }

    override fun renderEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.NetworkError -> {
                event.throwable.localizedMessage?.let { message ->
                    Snackbar.make(
                        vgContainer,
                        message,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    vgState.setState(State.Error, message, true)
                }
            }
        }
    }

    override fun onSelectSortOption(option: String) {
        homeViewModel.page = 1
        homeViewModel.orderBy = option
        fetchPhoto()
    }

    private fun initSpinner() {
        var sortOptions: ArrayList<SortOption> = ArrayList()
        sortOptions.add(SortOption(ORDER[0], false))
        sortOptions.add(SortOption(ORDER[1], true))

        sortSpinnerAdapter =
            SortSpinnerAdapter(this, R.layout.item_sort, R.id.tvSortOption, sortOptions, this)
        spOrder.adapter = sortSpinnerAdapter

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
                        tvCategory.text = homeViewModel.selectedCategoryName
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

    private fun createPhotoRequest(isRefreshedAll: Boolean = true): PhotoRequest {
        return PhotoRequest(
            homeViewModel.page,
            homeViewModel.selectedCategoryName,
            homeViewModel.orderBy,
            isRefreshedAll
        )
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        ivFilter.setOnClickListener {
            spCategories.performClick()
        }
        ivSort.setOnClickListener {
            spOrder.performClick()
        }
    }
}
