package com.engdiary.mureng.ui.write_diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.ItemWritingDiaryImage
import com.engdiary.mureng.databinding.ItemWritingdiaryimageImageBinding


class ItemWritingDiaryImageViewHolder private constructor(
    private val binding: ItemWritingdiaryimageImageBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        itemWritingDiaryImage: ItemWritingDiaryImage,
        onClickListener: WritingDiaryImageAdapter.OnClickListener
    ) {
        binding.itemWritingDiaryImage = itemWritingDiaryImage
        binding.onClickListener = onClickListener
    }


    companion object {
        const val viewType = 1

        fun from(parent: ViewGroup): ItemWritingDiaryImageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ItemWritingDiaryImageViewHolder(ItemWritingdiaryimageImageBinding.inflate(inflater))
        }
    }
}
