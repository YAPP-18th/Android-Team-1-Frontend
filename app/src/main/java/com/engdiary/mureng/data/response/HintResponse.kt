package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.Hint
import com.google.gson.annotations.SerializedName

data class HintResponse(
    @SerializedName("hintId")
    val id: Int,
    @SerializedName("meaning")
    val meaning: String,
    @SerializedName("word")
    val word: String
){
    fun toDomain(): Hint = Hint(id, word, meaning)
}
