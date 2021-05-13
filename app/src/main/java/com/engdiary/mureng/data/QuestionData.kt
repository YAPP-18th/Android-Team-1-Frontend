package com.engdiary.mureng.data

data class QuestionData(
    val quesTitle : String,
    var quesCnt : Int = 0,
    val quesKor : String?,
    val quesUser : String?,
    val quesUserImg : String?
)
