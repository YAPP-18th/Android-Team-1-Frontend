package com.engdiary.mureng.data.response

import android.os.Parcelable
import com.engdiary.mureng.data.Author
import com.engdiary.mureng.data.Award
import com.engdiary.mureng.data.Badge
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AwardNetwork(
    val member : Author,
    val badges : List<Badge>,
    val requesterProfile : Boolean
) : Parcelable {
    fun asDomain(): Award = Award(
        member,
        badges,
        requesterProfile
    )
}

