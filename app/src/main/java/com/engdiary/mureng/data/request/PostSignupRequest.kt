package com.engdiary.mureng.data.request

import com.google.gson.annotations.SerializedName

data class PostSignupRequest (
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("image")
    val image: String,
)