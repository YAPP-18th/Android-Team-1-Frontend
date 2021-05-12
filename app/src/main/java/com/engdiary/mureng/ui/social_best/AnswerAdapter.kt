package com.engdiary.mureng.ui.social_best

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.AnswerData
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.databinding.ItemSocialAnswerBinding
import com.engdiary.mureng.databinding.ItemSocialQuesBinding


class AnswerAdapter(val vm: AnswerViewModel) :
    ListAdapter<AnswerData, AnswerViewHolder>(AnswerDiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemSocialAnswerBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_social_answer, parent, false)

        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AnswerViewHolder(private val binding: ItemSocialAnswerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(answerData: AnswerData) {
        binding.answerData = answerData

    }
}

object AnswerDiffUtilCallBack : DiffUtil.ItemCallback<AnswerData>() {
    override fun areItemsTheSame(
        oldItem: AnswerData,
        newItem: AnswerData
    ): Boolean {
        return oldItem.ansTitle == newItem.ansTitle
    }

    override fun areContentsTheSame(
        oldItem: AnswerData,
        newItem: AnswerData
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
