package com.engdiary.mureng.data.response

data class Response<T> (
    val data: T?,
    val timestamp: Long,
    val message: String,
)
