package com.engdiary.mureng.data.response

import com.google.gson.annotations.SerializedName

data class PostDiaryResponse(
    @SerializedName("content")
    val content: String,
    @SerializedName("image")
    val imagePath: String,
    @SerializedName("replyId")
    val diaryId: Int
)

