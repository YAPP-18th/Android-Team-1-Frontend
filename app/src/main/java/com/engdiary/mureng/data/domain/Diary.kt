package com.engdiary.mureng.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Diary(
    val author: Author,
    val content: DiaryContent,
    val image: String,
    val question: Question,
    val questionId: Int,
    val date: String,
    val id: Int,
    var likeCount: Int,
    val isMine: Boolean,
    var likeYn : Boolean
) : Parcelable {
    val clickedLikeYn: Boolean
        get() = !(likeYn ?: true)
}
