package com.engdiary.mureng.fcm

import android.app.NotificationManager
import androidx.core.content.ContextCompat
import com.engdiary.mureng.util.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MurengFCMService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        sendNotification(remoteMessage)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(remoteMessage, applicationContext)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}
