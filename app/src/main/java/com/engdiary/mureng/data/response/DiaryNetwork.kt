package com.engdiary.mureng.data.response

import android.os.Parcelable
import com.engdiary.mureng.data.domain.Diary
import com.engdiary.mureng.data.domain.DiaryContent
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    var likeCount: Int,
    @SerializedName("requestedByAuthor")
    val isMine: Boolean,
    @SerializedName("likedByRequester")
    val likeYn : Boolean,
): Parcelable {
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
            isMine,
            likeYn
        )
}
