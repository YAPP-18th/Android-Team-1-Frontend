package com.engdiary.mureng.data.response

import com.google.gson.annotations.SerializedName

data class PostDiaryImageResponse (
    @SerializedName("imagePath")
    val imagePath: String
)
