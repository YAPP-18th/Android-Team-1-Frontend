package com.engdiary.mureng.data.response

import com.google.gson.annotations.SerializedName

data class TodayExpression(
        @SerializedName("expId")
        val expId: Int,
        @SerializedName("expression")
        val expression: String,
        @SerializedName("meaning")
        val meaning: String,
        @SerializedName("expressionExample")
        val expressionExample: String,
        @SerializedName("expressionExampleMeaning")
        val expressionExampleMeaning: String,
        @SerializedName("scrappedByRequester")
        val scrappedByRequester: Boolean
)