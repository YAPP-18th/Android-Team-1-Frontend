package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.domain.QuestionRefresh
import com.google.gson.annotations.SerializedName

data class QuestionRefreshNetwork(
    @SerializedName("questionId")
    val questionId: Int,
//    @SerializedName("category")
//    val category: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("koContent")
    val contentKr: String,
    @SerializedName("repliesCount")
    val repliesCount: Int = 0

) : java.io.Serializable {
    fun asDomain(): QuestionRefresh =
        QuestionRefresh(content, contentKr, questionId, repliesCount)
}
