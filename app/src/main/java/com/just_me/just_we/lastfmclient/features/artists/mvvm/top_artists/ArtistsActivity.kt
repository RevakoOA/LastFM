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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.Menu
import com.just_me.just_we.lastfmclient.R
import com.just_me.just_we.lastfmclient.core.platform.BaseActivity
import com.just_me.just_we.lastfmclient.core.utils.getJsonFromRaw
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.di.ArtistsActivityComponent
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artists.di.ArtistsActivityModule
import com.just_me.just_we.lastfmclient.features.artists.mvvm.top_artistsBaseContract.BasePresenter.ArtistsContract
import kotlinx.android.synthetic.main.activity_artists.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ArtistsActivity : BaseActivity(), ArtistsContract.ActivityView {

    @Inject lateinit var artistsPresenter: ArtistsContract.Presenter
    lateinit var artistsActivityComponent: ArtistsActivityComponent

    companion object {
        fun callingIntent(context: Context) = Intent(context, ArtistsActivity::class.java)
    }

    override fun setTopInCountry(country: String) {
        tvTitle?.animateText("Top in $country")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        artistsActivityComponent = appComponent.getArtistsActivityComponent(ArtistsActivityModule(this))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artists)
        addFragment(savedInstanceState)
    }

    override fun fragment() = ArtistsFragment()


    override fun onStart() {
        super.onStart()
        initializeDrawerContent()

    }

    private fun initializeDrawerContent() {
        // get country list
        val list = getJsonFromRaw(R.raw.coutry_list, resources)
        val menu = nvCountries.menu
        for (item in list) {
            val menuItem = menu.add(R.id.gCountries, Menu.NONE, 0, item.name)
            if (item.name == "Ukraine") {
                menuItem.isChecked = true
            }

        }
        nvCountries.invalidate()
        // end get country list


        nvCountries.setNavigationItemSelectedListener {
            dlRoot.closeDrawer(GravityCompat.START)
            val country = it.title.toString()
            setTopInCountry(country)
            artistsPresenter.loadArtistsList(country)
            true
        }
    }
}
