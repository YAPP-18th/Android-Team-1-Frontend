package com.engdiary.mureng.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.Hint
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.databinding.ItemSocialQuesBinding
import com.engdiary.mureng.databinding.ItemTodayExpressionBinding
import com.engdiary.mureng.ui.social_best.BestPopularViewModel

//class QuestionViewHolder(private val binding: ItemSocialQuesBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//    fun bind(question: QuestionNetwork, vm: BestPopularViewModel) {
//        binding.quesData = question
//        binding.vm = vm
//
//    }
//}

class TodayExpressionHolder(private val binding: ItemTodayExpressionBinding):
        RecyclerView.ViewHolder(binding.root) {

    fun bind(tx: TodayExpression, vm: ScrapViewModel) {
        binding.expression = tx
        binding.vm = vm
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
