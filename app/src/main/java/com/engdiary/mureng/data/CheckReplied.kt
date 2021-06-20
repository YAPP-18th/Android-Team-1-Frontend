package com.engdiary.mureng.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckReplied (
        val replied: Boolean
): Parcelable