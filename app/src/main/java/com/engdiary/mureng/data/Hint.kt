package com.engdiary.mureng.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hint (
    val id: Int,
    val word: String,
    val meaning: String
) : Parcelable
