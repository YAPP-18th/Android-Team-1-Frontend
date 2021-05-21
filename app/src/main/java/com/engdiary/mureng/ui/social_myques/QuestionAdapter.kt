package com.engdiary.mureng.ui.social_myques

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.databinding.ItemSocialQuesBinding
import com.engdiary.mureng.ui.social_best.PopularViewModel
import com.engdiary.mureng.util.setOnSingleClickListener


class QuestionAdapter(val vm: PopularViewModel) :
    ListAdapter<QuestionNetwork, QuestionViewHolder>(QuestionDiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemSocialQuesBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_social_ques, parent, false)

        return QuestionViewHolder(binding).apply {
            binding.root.setOnSingleClickListener {
                vm.questionItemClick(binding.quesData!!)
            }
        }
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(getItem(position), vm)
    }
}

class QuestionViewHolder(private val binding: ItemSocialQuesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(question: QuestionNetwork, vm: PopularViewModel) {
        binding.quesData = question
        binding.vm = vm

    }
}

object QuestionDiffUtilCallBack : DiffUtil.ItemCallback<QuestionNetwork>() {
    override fun areItemsTheSame(
        oldItem: QuestionNetwork,
        newItem: QuestionNetwork
    ): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(
        oldItem: QuestionNetwork,
        newItem: QuestionNetwork
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
