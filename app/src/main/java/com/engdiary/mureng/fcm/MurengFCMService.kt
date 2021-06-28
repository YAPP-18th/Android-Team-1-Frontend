package com.engdiary.mureng.fcm

import android.app.NotificationManager
import androidx.core.content.ContextCompat
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.util.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ServiceScoped
class MurengFCMService:FirebaseMessagingService() {
    @Inject lateinit var ioScope: CoroutineScope
    @Inject lateinit var murengRepository: MurengRepository

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

    override fun onNewToken(fcmToken: String) {
        super.onNewToken(fcmToken)
        postFcmTokenToServer(fcmToken)
    }

    private fun postFcmTokenToServer(fcmToken: String) {
        ioScope.launch(CoroutineExceptionHandler { _, throwable -> Timber.d(throwable) }) {
            murengRepository.postFCMToken(fcmToken)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.cancel()
    }
}
