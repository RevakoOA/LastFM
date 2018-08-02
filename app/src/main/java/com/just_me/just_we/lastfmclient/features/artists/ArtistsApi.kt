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
package com.just_me.just_we.lastfmclient.features.artists

import com.just_me.just_we.lastfmclient.entity.Artist
import com.just_me.just_we.lastfmclient.entity.ArtistDetails
import com.just_me.just_we.lastfmclient.entity.TopArtists
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ArtistsApi {
    companion object {
        private const val PARAM_ARTIST_ID = "mbid"
        private const val country = "country"
    }

    @GET("?method=geo.gettopartists")
    fun artists(@Query("country") country: String): Call<TopArtists>

    @GET("?method=artist.getinfo")
    fun artistDetails(@Query("artist")artistName: String): Call<Artist>
}
