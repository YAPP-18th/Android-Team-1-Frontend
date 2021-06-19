package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.User
import com.engdiary.mureng.data.Question
import com.google.gson.annotations.SerializedName

data class UserNetwork(
        @SerializedName("memberId")
        val memberId: Int,
        @SerializedName("identifier")
        val identifier: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("image")
        val image: String?,
        @SerializedName("murengCount")
        val murengCount: Int,
        @SerializedName("attendanceCount")
        val attendanceCount: Int,
        @SerializedName("pushActive")
        val pushActive: Boolean

) : java.io.Serializable {
    fun asDomain(): User =
            User(memberId, identifier, email, nickname, image, murengCount, attendanceCount, pushActive)
}
