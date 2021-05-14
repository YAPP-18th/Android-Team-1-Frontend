package com.engdiary.mureng.ui.write_diary

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.ItemWritingDiaryImage

class WritingDiaryImageAdapter(
    private val onGalleryClickListener: OnGalleryClickListener,
    private val onItemClickListener: OnClickListener,
    private var items: List<ItemWritingDiaryImage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        items = items.toMutableList()
            .apply { add(ItemWritingDiaryImage.Gallery()) }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ItemWritingDiaryImage.Gallery -> ItemWritingDiaryGalleryViewHolder.viewType
            is ItemWritingDiaryImage.DiaryImage -> ItemWritingDiaryImageViewHolder.viewType
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemWritingDiaryGalleryViewHolder.viewType -> ItemWritingDiaryGalleryViewHolder.from(
                parent
            )

            ItemWritingDiaryImageViewHolder.viewType -> ItemWritingDiaryImageViewHolder.from(parent)
            else -> throw IllegalArgumentException(
                "WritingDiaryImageAdapter와 사용할 수 없는 viewtype 입니다. (잘못된 viewType: $viewType)"
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemWritingDiaryGalleryViewHolder -> {
                holder.bind(onGalleryClickListener)
            }
            is ItemWritingDiaryImageViewHolder -> {
                holder.bind(items[position], onItemClickListener)
            }
        }
    }

    override fun getItemCount(): Int = items.size


    class OnClickListener(private val onClickListener: (ItemWritingDiaryImage, View) -> Unit) {
        fun onClick(item: ItemWritingDiaryImage, view: View) = onClickListener(item, view)
    }

    class OnGalleryClickListener(private val onClickListener: () -> Unit) {
        fun onClick() = onClickListener()
    }
}
