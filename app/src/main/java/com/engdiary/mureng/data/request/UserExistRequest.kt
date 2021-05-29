package com.engdiary.mureng.data.request

import com.google.gson.annotations.SerializedName


data class UserExistRequest (
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)