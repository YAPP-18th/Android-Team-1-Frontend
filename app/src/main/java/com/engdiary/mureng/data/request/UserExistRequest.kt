package com.engdiary.mureng.data.request

import com.google.gson.annotations.SerializedName


data class UserExistRequest (
    @SerializedName("providerAccessToken")
    val providerAccessToken: String,
    @SerializedName("providerName")
    val providerName: String
)