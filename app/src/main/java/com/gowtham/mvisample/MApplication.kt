package com.gowtham.mvisample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}