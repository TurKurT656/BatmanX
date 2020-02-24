package io.omido.batmanx.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response


private const val API_KEY = "apikey"
private const val API_KEY_VALUE = "3e974fca"

object ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val url = original.url.newBuilder()
            .addQueryParameter(API_KEY, API_KEY_VALUE)
            .build()

        val request = original
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}