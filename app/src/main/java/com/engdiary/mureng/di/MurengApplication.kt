package com.engdiary.mureng.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MurengApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(
            Timber.DebugTree()
        )
    }
}