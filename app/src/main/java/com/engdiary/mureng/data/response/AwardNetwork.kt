package com.engdiary.mureng.data.response

import android.os.Parcelable
import com.engdiary.mureng.data.domain.Author
import com.engdiary.mureng.data.domain.Award
import com.engdiary.mureng.data.domain.Badge
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

