package com.engdiary.mureng.data.request

import com.google.gson.annotations.SerializedName

data class PostSignupRequest (
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("nickname")
    val nickname : String,
        @SerializedName("email")
    var email: String
)

data class PostJWTRequest(
    @SerializedName("email")
    val email: String
)