package com.engdiary.mureng.ui.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.databinding.ItemRecordBinding
import com.engdiary.mureng.util.setRound

class RecordViewHolder private constructor(
    private val binding: ItemRecordBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(diary: Diary, onClickListener: RecordAdapter.OnClickListener) {
        binding.onClickListener = onClickListener
        binding.diary = diary
    }

    fun setDiaryImageRadius(radius: Float){
        binding.imageviewRecordImage.setRound(radius)
    }

    companion object {
        private const val radius = 8.2f

        fun from(parent: ViewGroup): RecordViewHolder {
            return ItemRecordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { RecordViewHolder(it) }
                .apply { setDiaryImageRadius(radius) }
        }
    }
}
