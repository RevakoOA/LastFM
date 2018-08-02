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
import com.just_me.just_we.lastfmclient.core.platform.BaseFragment
import com.just_me.just_we.lastfmclient.R
import com.just_me.just_we.lastfmclient.features.artists.ArtistFailure.ListNotAvailable
import com.just_me.just_we.lastfmclient.core.exception.Failure
import com.just_me.just_we.lastfmclient.core.exception.Failure.NetworkConnection
import com.just_me.just_we.lastfmclient.core.exception.Failure.ServerError
import com.just_me.just_we.lastfmclient.core.extension.failure
import com.just_me.just_we.lastfmclient.core.extension.invisible
import com.just_me.just_we.lastfmclient.core.extension.observe
import com.just_me.just_we.lastfmclient.core.extension.viewModel
import com.just_me.just_we.lastfmclient.core.extension.visible
import com.just_me.just_we.lastfmclient.core.navigation.Navigator
import kotlinx.android.synthetic.main.fragment_artists.emptyView
import kotlinx.android.synthetic.main.fragment_artists.movieList
import javax.inject.Inject

class ArtistsFragment : BaseFragment() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var artistsAdapter: ArtistsAdapter

    private lateinit var artistsViewModel: ArtistsViewModel

    override fun layoutId() = R.layout.fragment_artists

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        artistsViewModel = viewModel(viewModelFactory) {
            observe(artists, ::renderArtistsList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadArtistList()
    }


    private fun initializeView() {
        movieList.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        movieList.adapter = artistsAdapter
        artistsAdapter.clickListener = { artist, navigationExtras ->
                    navigator.showArtistDetails(activity!!, artist, navigationExtras) }
    }

    private fun loadArtistList() {
        emptyView.invisible()
        movieList.visible()
        showProgress()
        artistsViewModel.loadArtists()
    }

    private fun renderArtistsList(artistPosters: List<ArtistPosterModel>?) {
        artistsAdapter.collection = artistPosters.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        movieList.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadArtistList)
    }
}
