package com.engdiary.mureng.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.databinding.ItemTodayExpressionBinding
import com.engdiary.mureng.util.setOnSingleClickListener

class TodayExpressionAdapter(private val expressions: List<TodayExpression>, val vm: ScrapViewModel) : RecyclerView.Adapter<TodayExpressionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayExpressionHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemTodayExpressionBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_today_expression, parent, false
        )

        return TodayExpressionHolder(binding).apply {
            binding.root.setOnSingleClickListener {
                vm.addScrap(binding.expression!!)
            }
        }

    }

    override fun onBindViewHolder(holder: TodayExpressionHolder, position: Int) {
        holder.bind(expressions[position], vm = vm)
    }

    override fun getItemCount(): Int = expressions.size
}



