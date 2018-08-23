package com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.di

import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistsActivityScope
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistsFragmentScope
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artistsBaseContract.BasePresenter.ArtistsContract
import dagger.Module
import dagger.Provides

@Module
class ArtistsActivityModule(var view: ArtistsContract.ActivityView) {
    @Provides @ArtistsActivityScope
    fun provideArtistActivityView() = view
}