package com.engdiary.mureng.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.engdiary.mureng.R
import com.engdiary.mureng.ui.main.MainActivity
import com.google.firebase.messaging.RemoteMessage

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
    remoteMessage: RemoteMessage,
    applicationContext: Context
) {
    val notificationBuilder =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification.Builder(applicationContext, remoteMessage.notification?.channelId)
        } else {
            Notification.Builder(applicationContext)
        }

    val pendingIntent = Intent(applicationContext, MainActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        .let {
            PendingIntent.getActivity(
                applicationContext,
                NOTIFICATION_ID,
                it,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

    notificationBuilder.setContentTitle(remoteMessage.notification?.title)
        .setContentText(remoteMessage.notification?.body)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, notificationBuilder.build())
}
