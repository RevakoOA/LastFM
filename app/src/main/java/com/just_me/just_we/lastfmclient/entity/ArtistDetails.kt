package com.just_me.just_we.lastfmclient.entity

import com.google.gson.annotations.SerializedName


data class ArtistDetails(
    @SerializedName("artist") val artist: Artist = Artist()
)
