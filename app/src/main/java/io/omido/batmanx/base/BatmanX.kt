package io.omido.batmanx.base

import android.app.Application
import io.omido.batmanx.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

/**
 * Custom application class
 */
class BatmanX : Application() {

    override fun onCreate() {
        super.onCreate()

        // Plant Timber logger in
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        // Initialize Koin DI
        startKoin {
            androidContext(this@BatmanX)
            androidLogger(Level.DEBUG)
            modules(
                // List of modules goes here
            )
        }


    }

}