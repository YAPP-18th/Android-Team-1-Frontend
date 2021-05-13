package com.engdiary.mureng.ui.social_myques

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.databinding.ItemSocialQuesBinding


class QuestionAdapter(val vm: QuestionViewModel) :
    ListAdapter<QuestionData, QuestionViewHolder>(QuestionDiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemSocialQuesBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_social_ques, parent, false)

        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class QuestionViewHolder(private val binding: ItemSocialQuesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(questionData: QuestionData) {
        binding.quesData = questionData

    }
}

object QuestionDiffUtilCallBack : DiffUtil.ItemCallback<QuestionData>() {
    override fun areItemsTheSame(
        oldItem: QuestionData,
        newItem: QuestionData
    ): Boolean {
        return oldItem.quesTitle == newItem.quesTitle
    }

    override fun areContentsTheSame(
        oldItem: QuestionData,
        newItem: QuestionData
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
