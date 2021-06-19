package com.engdiary.mureng.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.Hint
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.ui.write_diary.HintViewHolder

class TodayExpressionAdapter(private val expressions: List<TodayExpression>) : RecyclerView.Adapter<TodayExpressionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayExpressionHolder {
        return TodayExpressionHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodayExpressionHolder, position: Int) {
        holder.bind(expressions[position])
    }

    override fun getItemCount(): Int = expressions.size
}
