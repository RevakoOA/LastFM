package com.just_me.just_we.lastfmclient.entity
import com.google.gson.annotations.SerializedName



data class CountryItem(
    @SerializedName("name") val name: String = "",
    @SerializedName("code") val code: String = ""
)