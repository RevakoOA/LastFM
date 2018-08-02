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
import com.just_me.just_we.lastfmclient.core.extension.*
import com.just_me.just_we.lastfmclient.core.platform.BaseFragment
import com.just_me.just_we.lastfmclient.entity.Artist
import com.just_me.just_we.lastfmclient.entity.ArtistDetails
import com.just_me.just_we.lastfmclient.features.artists.ArtistFailure.NonExistentArtist
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.ArtistPosterModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ArtistDetailsFragment : BaseFragment() {

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

    @Inject
    lateinit var artistDetailsAnimator: ArtistDetailsAnimator

    private lateinit var artistDetailsViewModel: ArtistDetailsViewModel

    override fun layoutId() = R.layout.fragment_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        activity?.let { artistDetailsAnimator.postponeEnterTransition(it) }

        artistDetailsViewModel = viewModel(viewModelFactory) {
            observe(artistDetails) { renderArtistDetails(it ?: Artist()) }
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            artistDetailsViewModel.loadArtistDetails((arguments?.get(PARAM_ARTIST) as ArtistPosterModel).name)
        } else {
            artistDetailsAnimator.scaleUpView(moviePlay)
            artistDetailsAnimator.cancelTransition(artistPoster)
            artistPoster.loadFromUrl((arguments!![PARAM_ARTIST] as ArtistPosterModel).posterUrl)
        }
    }

    override fun onBackPressed() {
        artistDetailsAnimator.fadeInvisible(scrollView, movieDetails)
        if (moviePlay.isVisible())
            artistDetailsAnimator.scaleDownView(moviePlay)
        else
            artistDetailsAnimator.cancelTransition(artistPoster)
    }

    private fun renderArtistDetails(artist: Artist) {
        with(artist) {
            activity.let {
                if (image.isNotEmpty()) {
                    artistPoster.loadUrlAndPostponeEnterTransition(image[image.size-1].url, it!!)
                }
                it?.toolbar?.title = artist.name
            }
            tvSummary.text = artist.bio.summary
            tvTags.text = artist.tags.tag.map { it.name }.joinToString()
            tvYear.text = artist.bio.published
            tvUrl.text = artist.url
//                moviePlay.setOnClickListener { artistDetailsViewModel.playMovie(trailer) }
        }
        artistDetailsAnimator.fadeVisible(scrollView, movieDetails)
        artistDetailsAnimator.scaleUpView(moviePlay)
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
