package com.engdiary.mureng.ui.write_diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.ItemWriteDiaryImage
import com.engdiary.mureng.databinding.ItemWritingdiaryimageGalleryBinding


class ItemWriteDiaryGalleryViewHolder private constructor(
    private val binding: ItemWritingdiaryimageGalleryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        itemWriteDiaryGallery: ItemWriteDiaryImage.Gallery,
        onClickListener: WriteDiaryImageAdapter.OnGalleryClickListener
    ) {
        binding.onClickListener = onClickListener
        binding.itemWritingDiaryGallery = itemWriteDiaryGallery
    }

    companion object {
        const val viewType = 0

        fun from(parent: ViewGroup, itemWidth: Int): ItemWriteDiaryGalleryViewHolder {
            return ItemWritingdiaryimageGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { ItemWriteDiaryGalleryViewHolder(it) }
                .apply {
                    itemView.layoutParams.height = itemWidth
                    itemView.layoutParams.width = itemWidth
                }
        }
    }
}
