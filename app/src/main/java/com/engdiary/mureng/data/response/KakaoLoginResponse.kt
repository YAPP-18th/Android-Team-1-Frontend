package com.engdiary.mureng.data.response

import com.google.gson.annotations.SerializedName

data class KakaoLoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("exist")
    val exist: Boolean
)

data class JWTResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
