package com.engdiary.mureng.data.request

import com.google.gson.annotations.SerializedName

data class NotificationRequest(
    @SerializedName( "pushActive")
    val isNotificationActive: Boolean
)
