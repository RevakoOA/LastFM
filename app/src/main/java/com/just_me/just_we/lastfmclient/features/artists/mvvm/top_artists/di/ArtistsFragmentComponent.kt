package com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di

import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.ArtistsActivity
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.ArtistsFragment
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.di.ArtistsFragmentModule
import dagger.Subcomponent

@ArtistsFragmentScope
@Subcomponent(modules = [ArtistsFragmentModule::class])
interface ArtistsFragmentComponent {
    fun inject(act: ArtistsActivity)
    fun inject(act: ArtistsFragment)
}