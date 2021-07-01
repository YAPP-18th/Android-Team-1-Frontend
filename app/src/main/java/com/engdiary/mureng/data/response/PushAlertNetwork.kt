package com.engdiary.mureng.data.response

import com.engdiary.mureng.data.domain.PushAlertSetting
import com.google.gson.annotations.SerializedName

data class PushAlertNetwork(
    @SerializedName("dailyPushActive")
    val isDailyPushAlertActive: Boolean,
    @SerializedName("likePushActive")
    val isLikePushAlertActive: Boolean
) {
    fun asDomain(): PushAlertSetting =
        PushAlertSetting(isDailyPushAlertActive, isLikePushAlertActive)
}
