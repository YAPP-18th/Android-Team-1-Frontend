package com.engdiary.mureng.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question (
    val category: String,
    val content: String,
    val contentKr: String,
    val questionId: Int,
    val repliesCount: Int,
    val hints: List<Hint>
) : Parcelable
