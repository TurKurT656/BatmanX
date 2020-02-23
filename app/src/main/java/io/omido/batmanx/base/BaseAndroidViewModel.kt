package io.omido.batmanx.base

import android.app.Application

abstract class BaseAndroidViewModel(
    application: Application
) : BaseViewModel() {

    protected val batmanXApp = application as BatmanX

}