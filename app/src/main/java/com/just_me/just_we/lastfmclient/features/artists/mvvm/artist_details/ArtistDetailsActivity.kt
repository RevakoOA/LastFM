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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.just_me.just_we.lastfmclient.R
import com.just_me.just_we.lastfmclient.core.extension.loadFromUrl
import com.just_me.just_we.lastfmclient.core.platform.BaseActivity
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistDetailsActivityComponent
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistDetailsActivityModule
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.ArtistPosterModel
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.di.ArtistsActivityComponent
import kotlinx.android.synthetic.main.activity_artist_details.*


class ArtistDetailsActivity : BaseActivity(), ArtistDetailsContract.ActivityView {

    lateinit var artistPosterModel: ArtistPosterModel
    lateinit var artistsActivityComponent: ArtistDetailsActivityComponent

    companion object {
        private const val INTENT_EXTRA_PARAM_ARTIST = "com.just_me.INTENT_PARAM_ARTIST"

        fun callingIntent(context: Context, artist: ArtistPosterModel): Intent {
            val intent = Intent(context, ArtistDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM_ARTIST, artist)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        artistsActivityComponent = appComponent.getArtistDetailsActivityComponent((ArtistDetailsActivityModule(this)))
        artistPosterModel = intent.getParcelableExtra(INTENT_EXTRA_PARAM_ARTIST)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_details)
        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        ivArtistPoster.loadFromUrl(artistPosterModel.posterUrl)
        ctTitle.title = artistPosterModel.name
    }

    override fun fragment() = ArtistDetailsFragment.forArtist(artistPosterModel)
}
