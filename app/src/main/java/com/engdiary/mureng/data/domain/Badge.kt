package com.engdiary.mureng.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Badge(
    val name : String,
    val content : String
) : Parcelable
