package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.domain.Author
import com.google.gson.annotations.SerializedName

data class AuthorNetwork(
    @SerializedName("attendanceCount")
    val attendanceCount: Int,
    @SerializedName("email")
    val email: String?,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("lastAttendanceDate")
    val lastAttendanceDate: String,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("murengCount")
    val murengCount: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("memberSetting")
    val pushAlertNetwork: PushAlertNetwork
) : java.io.Serializable  {
    fun asDomain(): Author = Author(
        attendanceCount,
        email,
        identifier,
        image,
        lastAttendanceDate,
        memberId,
        murengCount,
        nickname,
        pushAlertNetwork.asDomain()
    )
}
