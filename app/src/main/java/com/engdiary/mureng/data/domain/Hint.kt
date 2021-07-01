package com.engdiary.mureng.data.domain

import android.os.Parcelable
import com.engdiary.mureng.data.response.HintNetwork
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hint(
    val id: Int,
    val word: String,
    val meaning: String
) : Parcelable {
    fun asNetwork(): HintNetwork = HintNetwork(id, meaning, word)
}
