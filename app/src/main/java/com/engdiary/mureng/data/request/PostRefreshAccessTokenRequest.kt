package com.engdiary.mureng.data.request

import com.google.gson.annotations.SerializedName

data class PostRefreshAccessTokenRequest(
    @SerializedName("murengRefreshToken")
    val refreshToken: String
)
