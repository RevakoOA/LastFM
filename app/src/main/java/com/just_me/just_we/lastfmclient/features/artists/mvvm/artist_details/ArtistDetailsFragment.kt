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
package com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details

import android.os.Bundle
import android.view.View
import com.just_me.just_we.lastfmclient.R
import com.just_me.just_we.lastfmclient.core.exception.Failure
import com.just_me.just_we.lastfmclient.core.exception.Failure.NetworkConnection
import com.just_me.just_we.lastfmclient.core.exception.Failure.ServerError
import com.just_me.just_we.lastfmclient.core.extension.close
import com.just_me.just_we.lastfmclient.core.extension.failure
import com.just_me.just_we.lastfmclient.core.extension.observe
import com.just_me.just_we.lastfmclient.core.extension.viewModel
import com.just_me.just_we.lastfmclient.core.platform.BaseFragment
import com.just_me.just_we.lastfmclient.entity.Artist
import com.just_me.just_we.lastfmclient.features.artists.ArtistFailure.NonExistentArtist
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistDetailsFragmentComponent
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistDetailsFragmentModule
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.ArtistPosterModel
import kotlinx.android.synthetic.main.fragment_artist_details.*

class ArtistDetailsFragment : BaseFragment(), ArtistDetailsContract.FragmentView {

    lateinit var fragmentComponent: ArtistDetailsFragmentComponent

    companion object {
        private const val PARAM_ARTIST = "param_artist"

        fun forArtist(artist: ArtistPosterModel): ArtistDetailsFragment {
            val artistDetailsFragment = ArtistDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_ARTIST, artist)
            artistDetailsFragment.arguments = arguments

            return artistDetailsFragment
        }
    }

    private lateinit var artistDetailsViewModel: ArtistDetailsViewModel

    override fun layoutId() = R.layout.fragment_artist_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent = (activity as ArtistDetailsActivity).artistsActivityComponent.getFragmentComponent(ArtistDetailsFragmentModule(this))
        fragmentComponent.inject(this)
        fragmentComponent.inject(activity as ArtistDetailsActivity)
        artistDetailsViewModel = viewModel(viewModelFactory) {
            observe(artistDetails) { renderArtistDetails(it ?: Artist()) }
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            artistDetailsViewModel.loadArtistDetails((arguments?.get(PARAM_ARTIST) as ArtistPosterModel).name)
        }
    }

    override fun onBackPressed() {
    }

    private fun renderArtistDetails(artist: Artist) {
        tvSummary.text = artist.bio.summary
        tvTags.text = artist.tags.tag.map { it.name }.joinToString()
        tvYear.text = artist.bio.published
        tvUrl.text = artist.url
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> {
                notify(R.string.failure_network_connection); close()
            }
            is ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is NonExistentArtist -> {
                notify(R.string.failure_movie_non_existent); close()
            }
        }
    }
}

