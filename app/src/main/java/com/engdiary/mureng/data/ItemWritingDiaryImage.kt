package com.engdiary.mureng.data

sealed class ItemWritingDiaryImage {
    abstract val id: Int

    data class Gallery(
        override val id: Int = -1
    ) : ItemWritingDiaryImage()

    data class DiaryImage(
        override val id: Int,
        val url: String
    ) : ItemWritingDiaryImage()
}
