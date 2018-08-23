package com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.di

import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistsFragmentScope
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.ArtistsPresenterImpl
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artistsBaseContract.BasePresenter.ArtistsContract
import dagger.Module
import dagger.Provides

@Module
class ArtistsFragmentModule(var view: ArtistsContract.FragmentView) {
    @Provides @ArtistsFragmentScope
    fun provideArtistFragmentView() = view

    @Provides @ArtistsFragmentScope
    fun provideArtistsPresenter(activityView: ArtistsContract.ActivityView): ArtistsContract.Presenter
            = ArtistsPresenterImpl(activityView, view)
}