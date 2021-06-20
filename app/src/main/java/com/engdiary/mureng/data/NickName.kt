package com.engdiary.mureng.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NickName (
        val duplicated: Boolean
) : Parcelable
