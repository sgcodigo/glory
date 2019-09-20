package com.deevvdd.sample_rx.app.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codigo.mvi.rx.MviActivity
import com.deevvdd.sample_rx.R
import com.deevvdd.sample_rx.app.features.home.mvi.event.HomeEvent
import com.deevvdd.sample_rx.app.features.home.mvi.viewstate.HomeViewState
import com.deevvdd.sample_rx.app.features.home.widgets.PhotoAdapter
import com.deevvdd.sample_rx.app.utils.State
import com.deevvdd.sample_rx.app.utils.show
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * Created by heinhtet on 19,September,2019
 */
class HomeActivity : MviActivity<HomeViewModel, HomeViewState, HomeEvent>() {

    private lateinit var photoAdapter: PhotoAdapter
    override fun setUpLayout() {

    }

    override fun getViewModel(): HomeViewModel {
        return homeViewModel
    }

    override fun render(viewState: HomeViewState) {
        Timber.d("Home View State ${viewState.popularPhotoResult}")
        if (viewState.loading) {
            vgState.setState(State.Loading)
        } else {
            vgState.setState(State.None)
        }

        if (viewState.error != null) {
            vgState.setState(State.Error, viewState.error.localizedMessage)
        }

        if (viewState.popularPhotoResult != null) {
            photoAdapter.setPhoto(viewState.popularPhotoResult.hits)
        }
    }

    override fun renderEvent(event: HomeEvent) {
    }

    private val homeViewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        photoAdapter = PhotoAdapter()
        rvPhoto.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
        homeViewModel.fetchPopularPhoto(1)
    }
}