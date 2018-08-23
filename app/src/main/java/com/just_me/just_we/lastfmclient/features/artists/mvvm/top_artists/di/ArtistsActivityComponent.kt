package com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.di

import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistsActivityScope
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistsFragmentComponent
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di.ArtistsFragmentScope
import dagger.Subcomponent

@ArtistsActivityScope
@Subcomponent(modules = [ArtistsActivityModule::class])
interface ArtistsActivityComponent {
    fun getFragmentComponent(artistsFragmentModule: ArtistsFragmentModule?): ArtistsFragmentComponent
}