package com.engdiary.mureng.data.domain

import android.os.Parcelable
import com.engdiary.mureng.data.response.AuthorNetwork
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    val attendanceCount: Int,
    val email: String?,
    val identifier: String,
    val image: String?,
    val lastAttendanceDate: String,
    val memberId: Int,
    val murengCount: Int,
    val nickname: String,
    val pushAlertSetting: PushAlertSetting
) : Parcelable {
    fun asNetwork(): AuthorNetwork = AuthorNetwork(
        attendanceCount,
        email,
        identifier,
        image,
        lastAttendanceDate,
        memberId,
        murengCount,
        nickname,
        pushAlertSetting.asNetwork()
    )
}
