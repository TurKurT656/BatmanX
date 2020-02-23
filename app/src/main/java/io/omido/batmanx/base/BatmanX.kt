package io.omido.batmanx.base

import android.app.Application
import io.omido.batmanx.BuildConfig
import timber.log.Timber

/**
 * Custom application class
 */
class BatmanX : Application() {

    override fun onCreate() {
        super.onCreate()

        // Plant Timber logger in
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    }

}