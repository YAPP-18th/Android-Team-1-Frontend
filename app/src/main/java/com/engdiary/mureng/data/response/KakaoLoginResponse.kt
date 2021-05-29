package com.engdiary.mureng.data.response

import com.google.gson.annotations.SerializedName

data class KakaoLoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("exist")
    val exist: Boolean
)

//"email": "string",
//"exist": true

