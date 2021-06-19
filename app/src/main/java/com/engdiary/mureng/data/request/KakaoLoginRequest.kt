package com.engdiary.mureng.data.request

data class KakaoLoginRequest(
    val userId : Long,
    val kakaoToken : String
)

data class KaKaoUser(
    val email: String,
    val nickname: String,
    val image: String,
)
