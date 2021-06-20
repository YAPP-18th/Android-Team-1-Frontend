package com.engdiary.mureng.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import android.graphics.Color
import android.os.Build
import com.engdiary.mureng.R
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MurengApplication : Application() {
    companion object {
        var appContext: Context? = null

        fun getGlobalApplicationContext(): Context {
            checkNotNull(appContext) { "This Application does not inherit com.kakao.GlobalApplication" }
            return appContext!!
        }

        fun getGlobalAppApplication(): MurengApplication {
            checkNotNull(appContext) { "This Application does not inherit com.kakao.GlobalApplication" }
            return appContext!! as MurengApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        KakaoSdk.init(this, appKey = "a53af2544ace9f28b4a214707c220ec3")

        Timber.plant(
            Timber.DebugTree()
        )

        createChannel(
            getString(R.string.like_channel_id),
            getString(R.string.like_channel_name),
            getString(R.string.like_channel_description)
        )
        createChannel(
            getString(R.string.daily_channel_id),
            getString(R.string.daily_channel_name),
            getString(R.string.daily_channel_description)
        )
    }

    fun createChannel(channelId: String, channelName: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { setShowBadge(false) }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = channelDescription

            this.getSystemService(NotificationManager::class.java)
                .run { createNotificationChannel(notificationChannel) }
        }
    }
}
