package com.engdiary.mureng.ui.write_diary

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.ItemWriteDiaryImage

class WriteDiaryImageAdapter(
    private val itemWidth: Int,
    private val onGalleryClickListener: OnGalleryClickListener,
    private val onItemClickListener: OnClickListener,
) : ListAdapter<ItemWriteDiaryImage, RecyclerView.ViewHolder>(DiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ItemWriteDiaryImage.Gallery -> ItemWriteDiaryGalleryViewHolder.viewType
            is ItemWriteDiaryImage.DiaryImage -> ItemWriteDiaryImageViewHolder.viewType
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemWriteDiaryGalleryViewHolder.viewType -> ItemWriteDiaryGalleryViewHolder.from(
                parent, itemWidth
            )

            ItemWriteDiaryImageViewHolder.viewType -> ItemWriteDiaryImageViewHolder.from(
                parent,
                itemWidth
            )
            else -> throw IllegalArgumentException(
                "WritingDiaryImageAdapter와 사용할 수 없는 viewtype 입니다. (잘못된 viewType: $viewType)"
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemWriteDiaryGalleryViewHolder -> {
                holder.bind(
                    getItem(position) as ItemWriteDiaryImage.Gallery,
                    onGalleryClickListener
                )
            }
            is ItemWriteDiaryImageViewHolder -> {
                holder.bind(
                    getItem(position) as ItemWriteDiaryImage.DiaryImage,
                    onItemClickListener
                )
            }
        }
    }

    override fun submitList(list: List<ItemWriteDiaryImage>?) {
        list?.toMutableList()
            ?.apply { add(0, ItemWriteDiaryImage.Gallery()) }
            ?.let { super.submitList(it) }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ItemWriteDiaryImage>() {
        override fun areItemsTheSame(
            oldItem: ItemWriteDiaryImage,
            newItem: ItemWriteDiaryImage
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemWriteDiaryImage,
            newItem: ItemWriteDiaryImage
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    class OnClickListener(private val onClickListener: (ItemWriteDiaryImage.DiaryImage) -> Unit) {
        fun onClick(item: ItemWriteDiaryImage.DiaryImage) =
            onClickListener(item)
    }

    class OnGalleryClickListener(private val onClickListener: (ItemWriteDiaryImage.Gallery) -> Unit) {
        fun onClick(item: ItemWriteDiaryImage.Gallery) = onClickListener(item)
    }
}
