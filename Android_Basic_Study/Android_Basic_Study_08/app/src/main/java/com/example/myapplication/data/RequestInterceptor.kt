package com.example.myapplication.data

import com.example.myapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().run {
            addHeader("Accept-Version", "v1")
            addHeader("Authorization", "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
            build()
        }

        return chain.proceed(request)
    }
}