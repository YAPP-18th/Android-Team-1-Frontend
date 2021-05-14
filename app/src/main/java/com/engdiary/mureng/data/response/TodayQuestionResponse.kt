package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.TodayQuestion
import com.google.gson.annotations.SerializedName

data class TodayQuestionResponse(
    @SerializedName("category")
    val category: String,
    @SerializedName("content")
    val question: String,
    @SerializedName("koContent")
    val questionKr: String,
    @SerializedName("questionId")
    val questionId: Int,
    @SerializedName("wordHints")
    val hints: List<HintResponse>?,
) {
    fun toDomain(): TodayQuestion =
        TodayQuestion(questionId, question, questionKr, hints?.map { it.toDomain() })
}
