package com.engdiary.mureng.data.domain

import androidx.databinding.ObservableBoolean

sealed class ItemWriteDiaryImage {
    abstract val id: Int

    data class Gallery(
        override val id: Int = -1
    ) : ItemWriteDiaryImage()

    data class DiaryImage(
        override val id: Int,
        val url: String,
        val imagePath: String,
        val isSelected: ObservableBoolean = ObservableBoolean(false)
    ) : ItemWriteDiaryImage() {

        fun setIsSelected(isSelected: Boolean){
            this.isSelected.set(isSelected)
        }

        companion object{
            const val FROM_SERVER_ID = -2
        }
    }
}
