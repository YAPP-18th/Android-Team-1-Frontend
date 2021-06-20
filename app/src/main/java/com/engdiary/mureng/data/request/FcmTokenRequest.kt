package com.engdiary.mureng.data.request

import com.google.gson.annotations.SerializedName

data class FcmTokenRequest(
    @SerializedName("fcmToken")
    val fcmToken: String
)
