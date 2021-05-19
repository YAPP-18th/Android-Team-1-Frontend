package com.engdiary.mureng.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author (
    val attendanceCount: Int,
    val email: String,
    val identifier: String,
    val image: String,
    val lastAttendanceDate: String,
    val memberId: Int,
    val murengCount: Int,
    val nickname: String,
    val pushActive: Boolean
) : Parcelable
