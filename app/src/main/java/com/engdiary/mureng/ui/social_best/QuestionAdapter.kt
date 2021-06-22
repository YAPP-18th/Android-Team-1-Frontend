package com.engdiary.mureng.ui.social_best

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.databinding.ItemSocialQuesBinding
import com.engdiary.mureng.databinding.ItemSocialQuesPopBinding
import com.engdiary.mureng.util.setOnSingleClickListener

/**
 * Social_Best Tab, My QUES Tab 인기 질문, 내 질문 리스트  RecyclerView Adapter
 */
class QuestionAdapter(val type : QuestionRecyclerType, val vm: BestPopularViewModel) :
    ListAdapter<Question, RecyclerView.ViewHolder>(QuestionDiffUtilCallBack) {
    companion object {
        const val TYPE_QUES_POP = 0
        const val TYPE_QUES_MORE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when(type) {
           QuestionRecyclerType.TYPE_QUES_MORE -> {
                TYPE_QUES_MORE
            }
            else -> {
                TYPE_QUES_POP
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TYPE_QUES_MORE -> {
                val binding: ItemSocialQuesBinding =
                    DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_social_ques,
                        parent,
                        false
                    )

                QuestionViewHolder(binding).apply {
                    binding.root.setOnSingleClickListener {
                        vm.questionItemClick(binding.quesData!!)
                    }
                }
            }
            else -> {
                val binding: ItemSocialQuesPopBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.item_social_ques_pop, parent, false)

                QuestionPopViewHolder(binding).apply {
                    binding.root.setOnSingleClickListener {
                        vm.questionItemClick(binding.quesData!!)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            TYPE_QUES_MORE -> {
                (holder as QuestionViewHolder).bind(getItem(position), vm)
            }
            else -> {
                (holder as QuestionPopViewHolder).bind(getItem(position),vm)
            }
        }

    }
}

class QuestionViewHolder(private val binding: ItemSocialQuesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(question: Question, vm: BestPopularViewModel) {
        binding.quesData = question
        binding.vm = vm

    }
}

class QuestionPopViewHolder(private val binding: ItemSocialQuesPopBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(question: Question, vm: BestPopularViewModel) {
        binding.quesData = question
        binding.vm = vm

    }
}


object QuestionDiffUtilCallBack : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(
        oldItem: Question,
        newItem: Question
    ): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(
        oldItem: Question,
        newItem: Question
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
enum class QuestionRecyclerType { TYPE_QUES_POP , TYPE_QUES_MORE }

