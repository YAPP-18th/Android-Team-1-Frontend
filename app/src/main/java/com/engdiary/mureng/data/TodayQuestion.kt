package com.engdiary.mureng.data

data class TodayQuestion(
    val id: Int,
    val question: String,
    val questionKr: String,
    val hint: List<Hint>?,
)
