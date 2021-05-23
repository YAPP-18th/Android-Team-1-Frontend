package com.engdiary.mureng.ui.social_best

import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.databinding.ItemSocialAnswerBinding
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.util.setOnSingleClickListener
import jp.wasabeef.blurry.Blurry
import timber.log.Timber

/**
 * Social_Best Tab, My QUES Tab 인기 질문, 내 질문 리스트  RecyclerView Adapter
 */
class HorizontalAnswerAdapter(val handler: Handler ,val vm: BestPopularViewModel) :
    ListAdapter<DiaryNetwork, HorizontalAnswerViewHolder>(HorizontalAnswerDiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalAnswerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemSocialAnswerBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_social_answer, parent, false)

        return HorizontalAnswerViewHolder(binding).apply {
            binding.root.setOnSingleClickListener {
                vm.answerItemClick(binding.diary!!)
            }
        }
    }

    override fun getItemCount(): Int {
        Timber.e("count - ${super.getItemCount()}")
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: HorizontalAnswerViewHolder, position: Int) {
        holder.bind(getItem(position), vm, handler)
    }
}

class HorizontalAnswerViewHolder(private val binding: ItemSocialAnswerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(diary: DiaryNetwork, vm: BestPopularViewModel, handler: Handler) {
        binding.diary = diary
        binding.vm = vm
        handler.postDelayed(Runnable {
            Blurry.with(MurengApplication.appContext).sampling(1)
                .capture(binding.imgBestAnsImage).into(binding.imgBestAnsImage)
        }, 1000)


    }
}

object HorizontalAnswerDiffUtilCallBack : DiffUtil.ItemCallback<DiaryNetwork>() {
    override fun areItemsTheSame(
        oldItem: DiaryNetwork,
        newItem: DiaryNetwork
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DiaryNetwork,
        newItem: DiaryNetwork
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
