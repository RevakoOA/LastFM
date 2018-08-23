package com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists

import android.support.v4.app.Fragment
import com.just_me.just_we.lastfmclient.core.extension.viewModel
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artistsBaseContract.BasePresenter.ArtistsContract
import javax.inject.Inject

class ArtistsPresenterImpl constructor(val activityView: ArtistsContract.ActivityView,
                                       val fragmentView: ArtistsContract.FragmentView): ArtistsContract.Presenter {

    override fun loadArtistsList(country: String) {
        fragmentView.showEmptyView(false)
        fragmentView.showArtistsList(true)
        fragmentView.showProgress(true)
        fragmentView.loadArtistsList(country)
    }

}