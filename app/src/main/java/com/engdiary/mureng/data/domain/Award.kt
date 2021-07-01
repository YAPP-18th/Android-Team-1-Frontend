package com.engdiary.mureng.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Award(
    val member : Author,
    val badges : List<Badge>,
    val requesterProfile : Boolean
) : Parcelable

