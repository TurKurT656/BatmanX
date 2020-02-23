package io.omido.batmanx.util.ktx

import timber.log.Timber

fun logE(t: Throwable, message: String? = null) {
    Timber.e(t, message)
}

fun logD(message: String) {
    Timber.d(message)
}