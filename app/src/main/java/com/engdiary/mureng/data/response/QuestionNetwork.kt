package com.engdiary.mureng.data.response

import android.os.Parcelable
import com.engdiary.mureng.data.Question
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionNetwork(
    @SerializedName("questionId")
    val questionId: Int,
    @SerializedName("category")
    val category: String?,
    @SerializedName("content")
    val content: String,
    @SerializedName("koContent")
    val contentKr: String,
    @SerializedName("repliesCount")
    val repliesCount: Int = 0 ,
    @SerializedName("wordHints")
    val wordHints: List<HintNetwork>,
    @SerializedName("author")
    val author : AuthorNetwork?
) : Parcelable {
    fun asDomain(): Question =
        Question(category, content, contentKr, questionId, repliesCount, wordHints.map { it.asDomain() }, author?.asDomain())
}
