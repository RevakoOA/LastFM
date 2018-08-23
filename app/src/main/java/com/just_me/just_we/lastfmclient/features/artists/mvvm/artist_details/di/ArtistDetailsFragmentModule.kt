package com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di

import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.ArtistDetailsContract
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.ArtistDetailsPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class ArtistDetailsFragmentModule(var view: ArtistDetailsContract.FragmentView) {
    @Provides @ArtistDetailsFragmentScope
    fun provideArtistFragmentView() = view

    @Provides @ArtistDetailsFragmentScope
    fun provideArtistPresenter(activityView: ArtistDetailsContract.ActivityView): ArtistDetailsContract.Presenter
            = ArtistDetailsPresenterImpl(activityView, view)
}