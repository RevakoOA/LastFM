/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists

import android.os.Parcel
import com.just_me.just_we.lastfmclient.core.platform.KParcelable
import com.just_me.just_we.lastfmclient.core.platform.parcelableCreator

data class ArtistPosterModel(val id: String, val name: String, val posterUrl: String) :
        KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::ArtistPosterModel)
    }

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(id)
            writeString(name)
            writeString(posterUrl)
        }
    }
}
