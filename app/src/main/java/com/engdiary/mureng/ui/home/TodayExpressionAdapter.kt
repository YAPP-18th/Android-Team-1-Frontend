package com.engdiary.mureng.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.databinding.ItemTodayExpressionBinding
import com.engdiary.mureng.ui.social_best.QuestionAdapter
import com.engdiary.mureng.ui.social_best.QuestionRecyclerType
import com.engdiary.mureng.util.setOnSingleClickListener

class TodayExpressionAdapter(val type : ScrapListType ,private val expressions: List<TodayExpression>, val vm: ScrapViewModel) : RecyclerView.Adapter<TodayExpressionHolder>() {

    companion object {
        const val TYPE_HOME = 0
        const val TYPE_SCRAP = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayExpressionHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTodayExpressionBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_today_expression, parent, false
        )
        return when(viewType) {
            TYPE_HOME -> {
                return TodayExpressionHolder(binding).apply {
                    binding.imgBookmark.setOnClickListener {
                        vm.addScrap(binding.expression!!)
                    }
                }

            }
            else -> {

                return TodayExpressionHolder(binding).apply {
                    binding.imgBookmark.setOnSingleClickListener {
                        vm.deleteScrap(binding.expression!!)
                    }
                }

            }
        }




    }

    override fun getItemViewType(position: Int): Int {
        return when(type) {
           ScrapListType.TYPE_HOME -> {
               TYPE_HOME
            }
            else -> {
                TYPE_SCRAP
            }
        }
    }

    override fun onBindViewHolder(holder: TodayExpressionHolder, position: Int) {
        holder.bind(expressions[position], vm = vm)
    }

    override fun getItemCount(): Int = expressions.size
}

enum class ScrapListType { TYPE_HOME , TYPE_SCRAP }




