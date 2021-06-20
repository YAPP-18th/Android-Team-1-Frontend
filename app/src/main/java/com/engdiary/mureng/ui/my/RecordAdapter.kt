package com.engdiary.mureng.ui.my

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.engdiary.mureng.data.Diary

class RecordAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Diary, RecordViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordViewHolder = RecordViewHolder.from(parent)

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Diary>() {
        override fun areItemsTheSame(
            oldItem: Diary,
            newItem: Diary
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Diary,
            newItem: Diary
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    class OnClickListener(private val onClickListener: (Diary) -> Unit) {
        fun onClick(item: Diary) = onClickListener(item)
    }
}
