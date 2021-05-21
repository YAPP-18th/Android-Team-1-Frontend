package com.engdiary.mureng.ui.social_best

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.databinding.ItemSocialAnswerBinding
import com.engdiary.mureng.databinding.ItemSocialBestAnswerBinding
import com.engdiary.mureng.util.setOnSingleClickListener
import timber.log.Timber

/**
 * Social_Best Tab 인기 답변 RecyclerView Adapter
 */
class AnswerAdapter(val type: AnswerRecyclerType, val vm: BestPopularViewModel) :
    ListAdapter<DiaryNetwork, RecyclerView.ViewHolder>(AnswerDiffUtilCallBack) {
    companion object {
        const val TYPE_BEST = 0
        const val TYPE_BEST_MORE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (type == AnswerRecyclerType.TYPE_BEST) TYPE_BEST else TYPE_BEST_MORE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_BEST -> {
                val binding: ItemSocialAnswerBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.item_social_answer, parent, false
                )
                AnswerViewHolder(binding)
            }
            else -> {
                val binding: ItemSocialBestAnswerBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.item_social_best_answer, parent, false
                )
                AnswerBestViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_BEST -> {
                (holder as AnswerViewHolder).bind(getItem(position), vm)
            }
            else -> {
                (holder as AnswerBestViewHolder).bind(getItem(position), vm)
            }
        }
    }
}

class AnswerViewHolder(private val binding: ItemSocialAnswerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(diaryData: DiaryNetwork,vm: BestPopularViewModel) {
        binding.diary = diaryData
        binding.vm = vm
    }
}

class AnswerBestViewHolder(private val binding: ItemSocialBestAnswerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(diaryData: DiaryNetwork,vm: BestPopularViewModel) {
        binding.diary = diaryData
        binding.vm = vm
    }
}

object AnswerDiffUtilCallBack : DiffUtil.ItemCallback<DiaryNetwork>() {
    override fun areItemsTheSame(
        oldItem: DiaryNetwork,
        newItem: DiaryNetwork
    ): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(
        oldItem: DiaryNetwork,
        newItem: DiaryNetwork
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
enum class AnswerRecyclerType { TYPE_BEST , TYPE_BEST_MORE }
