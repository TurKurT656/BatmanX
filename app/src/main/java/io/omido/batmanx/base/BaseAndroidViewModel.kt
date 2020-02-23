package io.omido.batmanx.base

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.omido.batmanx.util.ktx.getColorCompat
import io.omido.batmanx.util.ktx.getDrawableCompat

abstract class BaseAndroidViewModel(
    application: Application
) : BaseViewModel() {

    val batmanXApp = application as BatmanXApp

    protected fun getString(@StringRes stringRes: Int, vararg formatArgs: Any?): String {
        return batmanXApp.resources.getString(stringRes, *formatArgs)
    }

    protected fun getColor(@ColorRes colorRes: Int): Int {
        return batmanXApp.applicationContext.getColorCompat(colorRes)
    }

    protected fun getDrawable(@DrawableRes drawableRes: Int): Drawable? {
        return batmanXApp.applicationContext.getDrawableCompat(drawableRes)
    }
}