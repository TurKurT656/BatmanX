package io.omido.batmanx.di.module

import com.google.gson.GsonBuilder
import io.omido.batmanx.BuildConfig
import io.omido.batmanx.data.network.interceptor.ApiKeyInterceptor
import io.omido.batmanx.data.network.ExclusionStrategy
import io.omido.batmanx.data.network.interceptor.CacheInterceptor
import io.omido.batmanx.di.Qualifiers.API_KEY_INTERCEPTOR
import io.omido.batmanx.di.Qualifiers.LOGGING_INTERCEPTOR
import io.omido.batmanx.di.Qualifiers.NETWORK_CACHE_FILE
import io.omido.batmanx.util.ktx.logD
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://www.omdbapi.com/"

// Default okhttp timeout -> sec
private const val TIMEOUT_DEBUG = 60L
private const val TIMEOUT_RELEASE = 20L

private const val CACHE_NAME = "network_cache"
private const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB

val networkModule = module {

    factory(NETWORK_CACHE_FILE) {
        File(androidContext().cacheDir, CACHE_NAME).apply {
            if (!exists()) mkdir()
        }
    }

    factory { Cache(get(NETWORK_CACHE_FILE), CACHE_SIZE) }

    factory {
        GsonBuilder()
            .setExclusionStrategies(ExclusionStrategy)
            .create()
    }

    single<Interceptor> {
        CacheInterceptor
    }

    single<Interceptor>(LOGGING_INTERCEPTOR) {
        HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    logD(
                        """
                            Network ->
                            $message
                        """.trimIndent()
                    )
                }
            }
        ).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<Interceptor>(API_KEY_INTERCEPTOR) {
        ApiKeyInterceptor()
    }

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get<Interceptor>(API_KEY_INTERCEPTOR))
            addInterceptor(get<Interceptor>(LOGGING_INTERCEPTOR))
            cache(get())
            val timeout = if (BuildConfig.DEBUG) TIMEOUT_DEBUG else TIMEOUT_RELEASE
            readTimeout(timeout, TimeUnit.SECONDS)
            writeTimeout(timeout, TimeUnit.SECONDS)
            connectTimeout(timeout, TimeUnit.SECONDS)
        }
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

}