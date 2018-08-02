package com.just_me.just_we.lastfmclient.entity

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class TopArtistsDeserialization: JsonDeserializer<TopArtists> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): TopArtists {
        val jsonObject = json?.asJsonObject?.get("topartists")
        return Gson().fromJson(jsonObject, TopArtists::class.java)
    }
}