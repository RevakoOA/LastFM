package com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di

import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.ArtistDetailsActivity
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.ArtistDetailsFragment
import dagger.Subcomponent

@ArtistDetailsFragmentScope
@Subcomponent(modules = [ArtistDetailsFragmentModule::class])
interface ArtistDetailsFragmentComponent {
    fun inject(act: ArtistDetailsActivity)
    fun inject(frag: ArtistDetailsFragment)
}