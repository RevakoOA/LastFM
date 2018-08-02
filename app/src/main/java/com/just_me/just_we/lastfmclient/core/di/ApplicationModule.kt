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
package com.just_me.just_we.lastfmclient.core.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.just_me.just_we.lastfmclient.BuildConfig
import com.just_me.just_we.lastfmclient.features.artists.ArtistsRepository
import com.just_me.just_we.lastfmclient.AndroidApplication
import com.just_me.just_we.lastfmclient.entity.Artist
import com.just_me.just_we.lastfmclient.entity.ArtistDetailsDeserialization
import com.just_me.just_we.lastfmclient.entity.TopArtists
import com.just_me.just_we.lastfmclient.entity.TopArtistsDeserialization
import com.just_me.just_we.lastfmclient.features.artists.ArtistsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {
    companion object {
        const val API_KEY = "API_KEY"
        const val FORMAT = "FORMAT"
        const val BASE_URL = "BASE_URL"
    }

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton @Named(API_KEY) fun provideApiKey() = "ccf205327d02b22006174d7e010a019b"
    @Provides @Singleton @Named(FORMAT) fun provideFormat() = "json"
    @Provides @Singleton @Named(BASE_URL) fun provideBaseUrl() = "http://ws.audioscrobbler.com/2.0/"

    @Provides @Singleton fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create(
                GsonBuilder()
                        .registerTypeAdapter(TopArtists::class.java, TopArtistsDeserialization())
                        .registerTypeAdapter(Artist::class.java, ArtistDetailsDeserialization())
                        .create())

    @Provides @Singleton fun provideRetrofit(@Named(API_KEY) apiKey: String,
                                             @Named(BASE_URL) baseUrl: String,
                                             @Named(FORMAT) format: String,
                                             gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createClient(apiKey, format))
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    private fun createClient(@Named(API_KEY) apiKey: String, @Named(FORMAT) format: String): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        val res = okHttpClientBuilder.addInterceptor(BaseInterceptor(apiKey, format)).build()
        return res
    }

    @Provides @Singleton fun provideMoviesRepository(dataSource: ArtistsRepository.Network): ArtistsRepository = dataSource
}
