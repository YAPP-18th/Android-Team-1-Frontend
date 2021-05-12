package com.engdiary.mureng.data

data class AnswerData(
    val image : String,
    val ansTitle : String,
    val ansDetail : String,
    val userName : String,
    var likeNum : Int = 0
)
