package com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.di

import dagger.Subcomponent

@ArtistsActivityScope
@Subcomponent(modules = [ArtistDetailsActivityModule::class])
interface ArtistDetailsActivityComponent {
    fun getFragmentComponent(artistDetailsModule: ArtistDetailsFragmentModule?): ArtistDetailsFragmentComponent
}