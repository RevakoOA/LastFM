package com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artistsBaseContract.BasePresenter

import com.just_me.just_we.lastfmclient.core.exception.Failure
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.ArtistPosterModel

interface ArtistsContract {

    interface ActivityView {

    }

    interface FragmentView {
        fun loadArtistsList(country: String)
        fun showEmptyView(b: Boolean)
        fun showArtistsList(b: Boolean)
        fun showProgress(b: Boolean)
        fun renderArtistsList(artistPosters: List<ArtistPosterModel>?)
        fun handleFailure(failure: Failure?)
    }

    interface Presenter {
        fun loadArtistsList(country: String)
    }
}