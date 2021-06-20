package com.engdiary.mureng.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Badge(
    val name : String,
    val content : String
) : Parcelable
