package com.engdiary.mureng.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionRefresh (
//    val category: String,
    val content: String,
    val koContent: String,
    val questionId: Int,
    val repliesCount: Int,
    ): Parcelable