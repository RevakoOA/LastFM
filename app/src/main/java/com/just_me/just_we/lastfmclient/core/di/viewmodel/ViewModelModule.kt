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
package com.just_me.just_we.lastfmclient.core.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.just_me.just_we.lastfmclient.features.artists.mvvm.artist_details.ArtistDetailsViewModel
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.ArtistsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ArtistsViewModel::class)
    abstract fun bindsMoviesViewModel(artistsViewModel: ArtistsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArtistDetailsViewModel::class)
    abstract fun bindsMovieDetailsViewModel(artistDetailsViewModel: ArtistDetailsViewModel): ViewModel
}