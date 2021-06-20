package com.engdiary.mureng.data

import android.os.Parcelable
import com.engdiary.mureng.data.Author
import com.engdiary.mureng.data.Badge
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Award(
    val member : Author,
    val badges : List<Badge>,
    val requesterProfile : Boolean
) : Parcelable

