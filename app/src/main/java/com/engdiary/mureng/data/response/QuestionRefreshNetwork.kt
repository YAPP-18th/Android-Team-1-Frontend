package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.DiaryContent
import com.engdiary.mureng.data.QuestionRefresh
import com.google.gson.annotations.SerializedName

data class QuestionRefreshNetwork(
    @SerializedName("questionId")
    val questionId: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("koContent")
    val contentKr: String,
    @SerializedName("repliesCount")
    val repliesCount: Int = 0

) : java.io.Serializable {
    fun asDomain(): QuestionRefresh =
        QuestionRefresh(category, content, contentKr, questionId, repliesCount)
}
