package com.engdiary.mureng.data.domain

import android.os.Parcelable
import com.engdiary.mureng.data.response.PushAlertNetwork
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PushAlertSetting(
    val isDailyPushAlertActive: Boolean,
    val isLikePushAlertActive: Boolean
) : Parcelable {
    fun asNetwork(): PushAlertNetwork =
        PushAlertNetwork(isDailyPushAlertActive, isLikePushAlertActive)
}
