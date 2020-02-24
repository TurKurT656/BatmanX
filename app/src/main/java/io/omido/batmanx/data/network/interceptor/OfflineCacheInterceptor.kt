package io.omido.batmanx.data.network.interceptor

import androidx.core.text.isDigitsOnly
import io.omido.batmanx.data.network.ConnectionLiveData
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

const val CACHE_MAX_STALE = "CACHE_MAX_STALE"

private const val CACHE_CONTROL = "Cache-Control"

/**
 * Adds Cache Control Header to the endpoints
 */
object OfflineCacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (ConnectionLiveData.value == false) {

            val maxStale = request.header(CACHE_MAX_STALE)
            if (!maxStale.isNullOrBlank() && maxStale.isDigitsOnly()) {
                request = request.newBuilder().header(
                    CACHE_CONTROL,
                    CacheControl.Builder().maxAge(
                        maxStale.toInt(),
                        TimeUnit.SECONDS
                    ).build().toString()
                ).build()
            }
        }

        return chain.proceed(request)
    }
}