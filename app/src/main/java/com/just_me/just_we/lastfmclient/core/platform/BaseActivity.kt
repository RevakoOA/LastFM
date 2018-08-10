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
package com.just_me.just_we.lastfmclient.core.platform

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.just_me.just_we.lastfmclient.R.id
import com.just_me.just_we.lastfmclient.R.layout
import com.just_me.just_we.lastfmclient.core.extension.inTransaction
import kotlinx.android.synthetic.main.toolbar.toolbar

/**
 * Base Activity class with helper methods for handling fragment transactions and back button
 * events.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_base)
        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
                id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    protected fun addFragment(savedInstanceState: Bundle?) {
                savedInstanceState ?: supportFragmentManager.inTransaction { add(
                id.fragmentContainer, fragment()) }
    }

    abstract fun fragment(): BaseFragment
}
