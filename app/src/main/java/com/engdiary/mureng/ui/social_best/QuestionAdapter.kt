package com.engdiary.mureng.ui.social_best

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.databinding.ItemSocialQuesBinding
import com.engdiary.mureng.util.setOnSingleClickListener

/**
 * Social_Best Tab, My QUES Tab 인기 질문, 내 질문 리스트  RecyclerView Adapter
 */
class QuestionAdapter(val vm: BestPopularViewModel) :
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
    fun getTotalCount() : Int {
        return if(vm!!.quesTotal!!.value!! == null ) 0 else vm!!.quesTotal!!.value!!
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(getItem(position), vm)
    }
}

class QuestionViewHolder(private val binding: ItemSocialQuesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(question: QuestionNetwork, vm: BestPopularViewModel) {
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
