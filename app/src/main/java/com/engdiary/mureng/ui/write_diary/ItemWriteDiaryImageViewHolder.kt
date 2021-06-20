package com.engdiary.mureng.ui.write_diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.ItemWriteDiaryImage
import com.engdiary.mureng.databinding.ItemWritingdiaryimageImageBinding


class ItemWriteDiaryImageViewHolder private constructor(
    private val binding: ItemWritingdiaryimageImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        itemWriteDiaryImage: ItemWriteDiaryImage.DiaryImage,
        onClickListener: WriteDiaryImageAdapter.OnClickListener
    ) {
        binding.itemWritingDiaryImage = itemWriteDiaryImage
        binding.onClickListener = onClickListener
    }

    private fun setSize(size: Int) {
        itemView.layoutParams.height = size
        itemView.layoutParams.width = size
    }

    companion object {
        const val viewType = 1

        fun from(parent: ViewGroup, itemWidth: Int): ItemWriteDiaryImageViewHolder {
            return ItemWritingdiaryimageImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { ItemWriteDiaryImageViewHolder(it) }
                .apply { setSize(itemWidth) }
        }
    }
}
