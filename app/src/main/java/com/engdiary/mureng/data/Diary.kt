package com.engdiary.mureng.data

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
    val likeCount: Int,
    val isMine: Boolean
) : Parcelable
