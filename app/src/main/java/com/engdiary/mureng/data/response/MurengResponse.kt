package com.engdiary.mureng.data.response

import com.google.gson.annotations.SerializedName

data class MurengResponse<T> (
    @SerializedName("data")
    val data: T?,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("message")
    val message: String,
    @SerializedName("currentPage")
    val currentPage : Int?,
    @SerializedName("totalPage")
    val totalPage : Int?,
    @SerializedName("pageSize")
    val pageSize : Int?
)
