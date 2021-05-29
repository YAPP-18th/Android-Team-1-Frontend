package com.engdiary.mureng.data.request

import com.google.gson.annotations.SerializedName

data class PostQuestioRequest (
    @SerializedName("category")
    val category: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("koContent")
    val koContent : String?
)