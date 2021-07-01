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
        var scrappedByRequester: Boolean?
) {
    val clickedScrap: Boolean
        get() = !(scrappedByRequester ?: true)

}
//{
//        fun asDomain(): Diary =
//                TodayExpression(
//                        author.asDomain(),
//                        DiaryContent.of(content),
//                        image,
//                        question.asDomain(),
//                        questionId,
//                        date,
//                        id,
//                        likeCount,
//                        isMine
//                )
//}
