package com.engdiary.mureng.data

data class AnswerData(
    val image : String,
    val ansTitle : String,
    val ansDetail : String,
    val userName : String,
    var likeNum : Int = 0,
    var likeYn: Boolean = false
) : java.io.Serializable {
    /** 만약 해당 [AnswerData] 아이템에 좋아요 버튼 클릭 시 새로 설정될 [likeYn] */
    val clickedLikeYn: Boolean
    get() = !(likeYn ?: true)
}
