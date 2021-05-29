package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.DiaryContent
import com.google.gson.annotations.SerializedName

data class DiaryNetwork(
    @SerializedName("author")
    val author: AuthorNetwork?,
    @SerializedName("content")
    val content: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("question")
    val question: QuestionNetwork,
    @SerializedName("questionId")
    val questionId: Int,
    @SerializedName("regDate")
    val date: String,
    @SerializedName("replyId")
    val id: Int,
    @SerializedName("replyLikeCount")
    val likeCount: Int,
    @SerializedName("requestedByAuthor")
    val isMine: Boolean,
    var likeYn : Boolean = false
) {
    fun asDomain(): Diary =
        Diary(
            author!!.asDomain(),
            DiaryContent.of(content),
            image,
            question.asDomain(),
            questionId,
            date,
            id,
            likeCount,
            isMine
        )
}
