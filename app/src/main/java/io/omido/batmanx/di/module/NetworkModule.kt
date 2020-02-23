package io.omido.batmanx.di.module

import io.omido.batmanx.BuildConfig
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://www.omdbapi.com/"

// Default okhttp timeout -> sec
private const val TIMEOUT_DEBUG = 60L
private const val TIMEOUT_RELEASE = 20L

val networkModule = module {

    single {
        OkHttpClient.Builder().apply {
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