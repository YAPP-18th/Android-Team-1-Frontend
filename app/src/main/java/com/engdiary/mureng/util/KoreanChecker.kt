package com.engdiary.mureng.util

object KoreanChecker {
    private const val NOT_KOREAN_REGEX_PATTERN = "[ㄱ-ㅎ|가-힣]"
    private val notKoreanRegex = NOT_KOREAN_REGEX_PATTERN.toRegex()

    fun isKoreanExist(expression: String): Boolean = notKoreanRegex.containsMatchIn(expression)
}