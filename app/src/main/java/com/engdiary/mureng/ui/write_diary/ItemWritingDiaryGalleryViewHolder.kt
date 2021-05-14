package com.engdiary.mureng.ui.write_diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.databinding.ItemWritingdiaryimageGalleryBinding


class ItemWritingDiaryGalleryViewHolder private constructor(
    private val binding: ItemWritingdiaryimageGalleryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        onClickListener: WritingDiaryImageAdapter.OnGalleryClickListener
    ) {
        binding.onClickListener = onClickListener
    }

    companion object {
        const val viewType = 0

        fun from(parent: ViewGroup): ItemWritingDiaryGalleryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ItemWritingDiaryGalleryViewHolder(ItemWritingdiaryimageGalleryBinding.inflate(inflater))
        }
    }
}
