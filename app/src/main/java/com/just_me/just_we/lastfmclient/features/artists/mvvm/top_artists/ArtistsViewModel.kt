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

import android.arch.lifecycle.MutableLiveData
import com.just_me.just_we.lastfmclient.core.platform.BaseViewModel
import com.just_me.just_we.lastfmclient.entity.TopArtists
import javax.inject.Inject

class ArtistsViewModel
@Inject constructor(private val getArtists: GetArtists) : BaseViewModel() {

    var artists: MutableLiveData<List<ArtistPosterModel>> = MutableLiveData()
    var country: MutableLiveData<String> = MutableLiveData()

    init {
        if (country.value.isNullOrEmpty()) {
            country.value = "Ukraine"
        }
    }

    fun loadArtists(country: String) {
        this.country.value = country
        getArtists(country) { it.either(::handleFailure, ::handleArtistList) }
    }

        private fun handleArtistList(artists: TopArtists) {
            this.artists.value = artists.artists.map { ArtistPosterModel(it.mbid, it.name, it.image.get(3).url) }
        }
    }