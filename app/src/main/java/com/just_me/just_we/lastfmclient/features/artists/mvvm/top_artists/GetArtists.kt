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

import com.just_me.just_we.lastfmclient.core.interactor.UseCase
import com.just_me.just_we.lastfmclient.entity.TopArtists
import com.just_me.just_we.lastfmclient.features.artists.ArtistsRepository
import javax.inject.Inject

class GetArtists
@Inject constructor(private val artistRepository: ArtistsRepository) : UseCase<TopArtists, String>() {

    override suspend fun run(params: String) = artistRepository.artists(params)
}
