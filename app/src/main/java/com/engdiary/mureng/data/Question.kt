package com.engdiary.mureng.data

import android.os.Parcelable
import com.engdiary.mureng.data.response.QuestionNetwork
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val category: String?,
    val content: String,
    val contentKr: String,
    val questionId: Int,
    val repliesCount: Int,
    val hints: List<Hint>,
    val author: Author?,
    var lineVisible : Boolean = true,
    var likeYn: Boolean? = null
) : Parcelable {
    fun asNetwork(): QuestionNetwork = QuestionNetwork(
        questionId,
        category,
        content,
        contentKr,
        repliesCount,
        hints.map { it.asNetwork() },
        author?.asNetwork()
    )

    val clickedLikeYn: Boolean
        get() = !(likeYn ?: true)
}
