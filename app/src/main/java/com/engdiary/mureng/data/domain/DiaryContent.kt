package com.engdiary.mureng.data.domain

import java.io.Serializable

class DiaryContent private constructor(
    val content: String
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiaryContent
        if (content != other.content) return false

        return true
    }

    override fun hashCode(): Int {
        return content.hashCode()
    }

    companion object {
        private const val KOREAN_REGEX_PATTERN = "[ㄱ-ㅎ|가-힣]"
        val KoreanRegex = KOREAN_REGEX_PATTERN.toRegex()
        const val CONTENT_MIN_LENGTH = 50

        fun of(content: String): DiaryContent {
            validateDiaryContentCondition(content)
            return DiaryContent(content)
        }

        private fun validateDiaryContentCondition(content: String) {
            require(checkLanguageCondition(content)) { "다이어리에는 한글이 포함될 수 없습니다. 입력받은 값: $content" }

            require(checkMinLengthCondition(content)) {
                "다이어리 내용은 최소 $CONTENT_MIN_LENGTH 이상이어야 합니다. 입력받은 값의 길이: ${content.length}"
            }
        }

        fun checkLanguageCondition(content: String): Boolean = !KoreanRegex.containsMatchIn(content)

        fun checkMinLengthCondition(content: String): Boolean = content.length >= CONTENT_MIN_LENGTH
    }
}
