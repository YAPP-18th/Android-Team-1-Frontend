package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.Question
import com.google.gson.annotations.SerializedName

data class QuestionNetwork(
    @SerializedName("questionId")
    val questionId: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("koContent")
    val contentKr: String,
    @SerializedName("repliesCount")
    val repliesCount: Int = 0 ,
    @SerializedName("wordHints")
    val wordHints: List<HintNetwork>,
    var lineVisible : Boolean = true
) : java.io.Serializable {
    fun asDomain(): Question =
        Question(category, content, contentKr, questionId, repliesCount, wordHints.map { it.asDomain() })
}
