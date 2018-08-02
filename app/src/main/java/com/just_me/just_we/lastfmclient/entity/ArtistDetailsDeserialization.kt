package com.just_me.just_we.lastfmclient.entity

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ArtistDetailsDeserialization: JsonDeserializer<Artist> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Artist {
        val jsonObject = json?.asJsonObject?.get("artist")
        return Gson().fromJson(jsonObject, Artist::class.java)
    }
}