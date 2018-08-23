package com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di

import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.ArtistDetailsContract
import dagger.Module
import dagger.Provides

@Module
class ArtistDetailsActivityModule(var view: ArtistDetailsContract.ActivityView) {
    @Provides @ArtistDetailsActivityScope
    fun provideArtistFragmentView() = view
}