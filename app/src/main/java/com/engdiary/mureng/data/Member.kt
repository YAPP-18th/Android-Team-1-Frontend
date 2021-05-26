package com.engdiary.mureng.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Member (
        val memberId: Int,
        val identifier: String,
        val email: String,
        val nickname: String,
        val image: String,
        val murengCount: Int,
        val attendanceCount: Int,
        val pushActive: Boolean
) : Parcelable

