package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.domain.Hint
import com.google.gson.annotations.SerializedName

data class HintNetwork(
    @SerializedName("hintId")
    val id: Int,
    @SerializedName("meaning")
    val meaning: String,
    @SerializedName("word")
    val word: String
) : java.io.Serializable {
    fun asDomain(): Hint = Hint(id, word, meaning)
}
