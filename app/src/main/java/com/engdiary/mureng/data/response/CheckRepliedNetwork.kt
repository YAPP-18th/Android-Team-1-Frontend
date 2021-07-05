package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.domain.CheckReplied
import com.google.gson.annotations.SerializedName

data class CheckRepliedNetwork(
        @SerializedName("replied")
        val replied: Boolean

) : java.io.Serializable {
    fun asDomain(): CheckReplied =
            CheckReplied(replied)
}
