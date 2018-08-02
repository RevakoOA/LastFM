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

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.just_me.just_we.lastfmclient.R
import com.just_me.just_we.lastfmclient.core.extension.inflate
import com.just_me.just_we.lastfmclient.core.extension.loadFromUrl
import com.just_me.just_we.lastfmclient.core.navigation.Navigator
import kotlinx.android.synthetic.main.item_artist_poster.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class ArtistsAdapter
@Inject constructor() : RecyclerView.Adapter<ArtistsAdapter.ViewHolder>() {

    internal var collection: List<ArtistPosterModel> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (ArtistPosterModel, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.item_artist_poster))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(artistPosterModel: ArtistPosterModel, clickListener: (ArtistPosterModel, Navigator.Extras) -> Unit) {
            itemView.artistPoster.loadFromUrl(artistPosterModel.posterUrl)
            itemView.tvName.text = artistPosterModel.name
            itemView.setOnClickListener { clickListener(artistPosterModel, Navigator.Extras(itemView.artistPoster)) }
        }
    }
}
