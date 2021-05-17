package com.engdiary.mureng.data.response

data class MurengResponse<T> (
    val data: T?,
    val timestamp: Long,
    val message: String,
)
