package com.owlyhoot

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OwlyHootApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}