package com.janegareeva.weatherforecast.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().addHeader(
            ACCEPT_HEADER,
            JSON_TYPE
        ).build()
        val response = chain.proceed(request)
        return response
    }

    companion object {
        private const val ACCEPT_HEADER = "Accept"
        private const val JSON_TYPE = "application/json"
    }
}