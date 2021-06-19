package com.engdiary.mureng.data.request

import com.google.gson.annotations.SerializedName

data class PostDiaryRequest(
    @SerializedName("questionId")
    val questionId: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("image")
    val imagePath: String
)
