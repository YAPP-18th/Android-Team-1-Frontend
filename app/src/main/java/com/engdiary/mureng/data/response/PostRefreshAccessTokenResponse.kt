package com.engdiary.mureng.data.response

import com.google.gson.annotations.SerializedName

data class PostRefreshAccessTokenResponse (
    @SerializedName("murengAccessToken")
    val accessToken: String,
    @SerializedName("murengRefreshToken")
    val refreshToken: String
)
