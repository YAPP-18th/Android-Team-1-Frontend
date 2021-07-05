package com.engdiary.mureng.data.response

import com.google.gson.annotations.SerializedName

data class DeleteDiaryResponse(
    @SerializedName("deleted")
    val isDeleted: Boolean
)