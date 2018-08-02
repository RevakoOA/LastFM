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
package com.just_me.just_we.lastfmclient.features.artists

import com.just_me.just_we.lastfmclient.core.exception.Failure
import com.just_me.just_we.lastfmclient.core.exception.Failure.NetworkConnection
import com.just_me.just_we.lastfmclient.core.exception.Failure.ServerError
import com.just_me.just_we.lastfmclient.core.functional.Either
import com.just_me.just_we.lastfmclient.core.functional.Either.Left
import com.just_me.just_we.lastfmclient.core.functional.Either.Right
import com.just_me.just_we.lastfmclient.core.platform.NetworkHandler
import com.just_me.just_we.lastfmclient.entity.Artist
import com.just_me.just_we.lastfmclient.entity.ArtistDetails
import com.just_me.just_we.lastfmclient.entity.TopArtists
import dagger.Provides
import retrofit2.Call
import javax.inject.Inject
interface ArtistsRepository {
    fun artists(country: String): Either<Failure, TopArtists>
    fun artistDetails(artistName: String): Either<Failure, Artist>


    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: ArtistsService) : ArtistsRepository {

        override fun artists(country: String): Either<Failure, TopArtists> {
            return when (networkHandler.isConnected) {
                true -> request(service.artists(country), {it}, TopArtists())
                false, null -> Left(NetworkConnection())
            }
        }

        override fun artistDetails(artistName: String): Either<Failure, Artist> {
            return when (networkHandler.isConnected) {
                true -> request(service.artistDetails(artistName), {it}, Artist())
                false, null -> Left(NetworkConnection())
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(transform((response.body() ?: default)))
                    false -> Left(ServerError())
                }
            } catch (exception: Throwable) {
                Left(ServerError())
            }
        }
    }
}
