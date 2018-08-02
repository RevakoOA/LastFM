package com.just_me.just_we.lastfmclient.core.utils


import android.content.res.Resources
import com.google.gson.Gson
import com.just_me.just_we.lastfmclient.core.extension.fromJson
import com.just_me.just_we.lastfmclient.entity.CountryItem
import java.util.*
import kotlin.collections.ArrayList

fun getJsonFromRaw(id: Int, res: Resources): ArrayList<CountryItem> {
    val inputStream = res.openRawResource(id);
    val scanner = Scanner(inputStream)
    val stringBuilder = StringBuilder()
    while (scanner.hasNextLine()) {
        stringBuilder.append(scanner.nextLine())
    }
    val list = Gson().fromJson<ArrayList<CountryItem>>(stringBuilder.toString())
    return list
}