package com.just_me.just_we.lastfmclient.core.di

import com.just_me.just_we.lastfmclient.core.di.ApplicationModule.Companion.API_KEY
import com.just_me.just_we.lastfmclient.core.di.ApplicationModule.Companion.FORMAT
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class BaseInterceptor @Inject constructor(@Named(API_KEY) val apiKey: String, @Named(FORMAT) val format: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        val url = request?.url()?.newBuilder()
                ?.addQueryParameter("api_key", apiKey)
                ?.addQueryParameter("format", format)
                ?.build()
        request = request?.newBuilder()?.url(url)?.build()
        return chain?.proceed(request)
    }
}