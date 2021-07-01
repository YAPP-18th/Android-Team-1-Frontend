package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.domain.NickName
import com.google.gson.annotations.SerializedName

data class NickNameNetwork(
        @SerializedName("duplicated")
        val duplicated: Boolean

) : java.io.Serializable {
    fun asDomain(): NickName = NickName(duplicated)
}