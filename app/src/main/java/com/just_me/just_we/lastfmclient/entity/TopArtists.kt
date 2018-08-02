package com.just_me.just_we.lastfmclient.entity
import com.google.gson.annotations.SerializedName


data class TopArtists(
        @SerializedName("artist") val artists: List<Artist> = ArrayList(),
        @SerializedName("@attr") val attr: Attr = Attr())

data class Attr(
    @SerializedName("country") val country: String = "",
    @SerializedName("page") val page: String = "",
    @SerializedName("perPage") val perPage: String = "",
    @SerializedName("totalPages") val totalPages: String = "",
    @SerializedName("total") val total: String = ""
)