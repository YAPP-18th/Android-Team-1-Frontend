package com.engdiary.mureng.ui.write_diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.domain.Hint
import com.engdiary.mureng.databinding.ItemHintBinding

class HintViewHolder private constructor(
    private val binding: ItemHintBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(hint: Hint) {
        binding.hint = hint
    }

    companion object {

        fun from(parent: ViewGroup): HintViewHolder {
            return ItemHintBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { HintViewHolder(it) }
        }
    }
}
