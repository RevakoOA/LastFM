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

import android.arch.lifecycle.MutableLiveData
import com.just_me.just_we.lastfmclient.core.platform.BaseViewModel
import com.just_me.just_we.lastfmclient.entity.Artist
import com.just_me.just_we.lastfmclient.entity.ArtistDetails
import com.just_me.just_we.lastfmclient.features.artists.PlayMovie
import javax.inject.Inject

class ArtistDetailsViewModel
@Inject constructor(private val getArtistDetails: GetArtistDetails,
                    private val playMovie: PlayMovie) : BaseViewModel() {

    var artistDetails: MutableLiveData<Artist> = MutableLiveData()

    fun loadArtistDetails(artistId: String) =
            getArtistDetails(GetArtistDetails.Params(artistId)) { it.either(::handleFailure, ::handleArtistDetails) }

    fun playMovie(url: String) = playMovie(PlayMovie.Params(url))

    private fun handleArtistDetails(artist: Artist) {
        this.artistDetails.value = artist
    }
}