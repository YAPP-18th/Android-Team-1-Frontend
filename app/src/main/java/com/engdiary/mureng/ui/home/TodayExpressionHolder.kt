package com.engdiary.mureng.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.Hint
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.databinding.ItemTodayExpressionBinding

class TodayExpressionHolder private constructor(
//    private val binding: ItemHintBinding
    private val binding: ItemTodayExpressionBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(tx: TodayExpression) {
        binding.expression = tx
    }

    companion object {

        fun from(parent: ViewGroup): TodayExpressionHolder {
            return ItemTodayExpressionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { TodayExpressionHolder(it) }
        }
    }
}
