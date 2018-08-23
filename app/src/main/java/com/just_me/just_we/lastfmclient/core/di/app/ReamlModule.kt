package com.just_me.just_we.lastfmclient.core.di.app

import com.just_me.just_we.lastfmclient.AndroidApplication
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class RealmModule {
    @Provides @Singleton fun provideRealm():Realm = Realm.getDefaultInstance()
//    @Provides @Singleton fun provideRealmService(): RealmService
}