package io.omido.batmanx.di

import org.koin.core.qualifier.named

object Qualifiers {

    val API_KEY_INTERCEPTOR = named("API_KEY_INTERCEPTOR")
    val LOGGING_INTERCEPTOR = named("LOGGING_INTERCEPTOR")

    val NETWORK_CACHE_FILE = named("NETWORK_CACHE_FILE")

}