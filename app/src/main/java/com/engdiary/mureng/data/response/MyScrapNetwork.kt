package com.engdiary.mureng.data.response

data class MyScrapNetwork(
        val member : AuthorNetwork,
        val requestProfile : Boolean,
        val scrapList : List<TodayExpression>?
)
