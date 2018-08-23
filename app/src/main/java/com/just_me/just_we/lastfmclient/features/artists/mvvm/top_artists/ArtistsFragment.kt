/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.just_me.just_we.lastfmclient.R
import com.just_me.just_we.lastfmclient.core.exception.Failure
import com.just_me.just_we.lastfmclient.core.exception.Failure.NetworkConnection
import com.just_me.just_we.lastfmclient.core.exception.Failure.ServerError
import com.just_me.just_we.lastfmclient.core.extension.*
import com.just_me.just_we.lastfmclient.core.navigation.Navigator
import com.just_me.just_we.lastfmclient.core.platform.BaseFragment
import com.just_me.just_we.lastfmclient.features.artists.ArtistFailure.ListNotAvailable
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistsFragmentComponent
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.di.ArtistsFragmentModule
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artistsBaseContract.BasePresenter.ArtistsContract
import kotlinx.android.synthetic.main.fragment_artists.*
import javax.inject.Inject

class ArtistsFragment : BaseFragment(), ArtistsContract.FragmentView {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var artistsAdapter: ArtistsAdapter
    @Inject lateinit var artistsPresenter: ArtistsContract.Presenter
    lateinit var fragmentComponent: ArtistsFragmentComponent

    private lateinit var artistsViewModel: ArtistsViewModel

    override fun showEmptyView(b: Boolean) {
        emptyView.visibility = if (b) VISIBLE else GONE
        artistList.visibility = if (b) GONE else VISIBLE

    }

    override fun showArtistsList(b: Boolean) {
        showEmptyView(!b)
    }

    override fun showProgress(b: Boolean) {
        if (b) showProgress() else hideProgress()
    }

    override fun loadArtistsList(country: String) {
        artistsViewModel.loadArtists(country)
    }

    override fun layoutId() = R.layout.fragment_artists

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent = (activity as ArtistsActivity).artistsActivityComponent.getFragmentComponent(ArtistsFragmentModule(this))
        fragmentComponent.inject(this)
        fragmentComponent.inject(activity as ArtistsActivity)
        artistsViewModel = viewModel(viewModelFactory) {
            observe(artists, ::renderArtistsList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewContent()
        artistsPresenter.loadArtistsList("Ukraine")
    }

    private fun initializeViewContent() {
        artistList.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        artistList.adapter = artistsAdapter
        artistsAdapter.clickListener = { artist, navigationExtras ->
                    navigator.showArtistDetails(activity!!, artist, navigationExtras) }
    }

    override fun renderArtistsList(artistPosters: List<ArtistPosterModel>?) {
        artistsAdapter.collection = artistPosters.orEmpty()
        hideProgress()
    }

    override fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        artistList.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh) {artistsPresenter.loadArtistsList("Ukraine")}
    }
}
