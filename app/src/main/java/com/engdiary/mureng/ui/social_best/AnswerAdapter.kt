package com.engdiary.mureng.ui.social_best

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.databinding.ItemSocialAnswerBinding
import com.engdiary.mureng.databinding.ItemSocialBestAnswerBinding
import com.engdiary.mureng.databinding.ItemSocialUserAnswerBinding
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.util.setOnSingleClickListener
import jp.wasabeef.blurry.Blurry
import timber.log.Timber

/**
 * Social_Best Tab 인기 답변 RecyclerView Adapter
 */
class AnswerAdapter(val type: AnswerRecyclerType, val vm: BestPopularViewModel, private val handler: android.os.Handler?) :
    ListAdapter<Diary, RecyclerView.ViewHolder>(AnswerDiffUtilCallBack) {
    companion object {
        const val TYPE_BEST = 0
        const val TYPE_BEST_MORE = 1
        const val TYPE_DETAIL = 2
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            AnswerRecyclerType.TYPE_BEST -> {
                TYPE_BEST
            }
            AnswerRecyclerType.TYPE_BEST_MORE -> {
                TYPE_BEST_MORE
            }
            else -> {
                TYPE_DETAIL
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_BEST -> {
                val binding: ItemSocialAnswerBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.item_social_answer, parent, false
                )
                AnswerViewHolder(binding).apply {
                    binding.root.setOnSingleClickListener {
                        vm.answerItemClick(binding.diary!!)
                    }
                }
            }
            TYPE_BEST_MORE -> {
                val binding: ItemSocialBestAnswerBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.item_social_best_answer, parent, false
                )
                AnswerBestViewHolder(binding).apply{
                    binding.root.setOnSingleClickListener {
                        vm.answerItemClick(binding.diary!!)
                    }
                    binding.clBestMoreAnswerHeart.setOnClickListener {
                        vm.answerItemHeartClick(binding.diary!!)
                        binding.diary = binding.diary!!.apply {
                            if(likeYn) likeCount -= 1
                            else likeCount += 1
                            likeYn = !this.likeYn!!
                        }

                    }
                }
            }
            else -> {
                val binding: ItemSocialUserAnswerBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.item_social_user_answer, parent, false
                )
                AnswerUserViewHolder(binding).apply {
                    binding.root.setOnSingleClickListener {
                        vm.answerItemClick(binding.diary!!)
                    }
                    binding.clSocialUserHeart.setOnClickListener {
                        vm.answerItemHeartClick(binding.diary!!)
                        binding.diary = binding.diary!!.apply {
                            if(likeYn) likeCount -= 1
                            else likeCount += 1
                            likeYn = !this.likeYn!!
                        }

                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_BEST -> {
                (holder as AnswerViewHolder).bind(getItem(position), vm, handler)
            }
            TYPE_BEST_MORE -> {
                (holder as AnswerBestViewHolder).bind(getItem(position), vm)
            }
            else -> {
                (holder as AnswerUserViewHolder).bind(getItem(position))
            }
        }
    }
}

class AnswerViewHolder(private val binding: ItemSocialAnswerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(diaryData: Diary, vm: BestPopularViewModel, handler: android.os.Handler?) {
        binding.diary = diaryData
        binding.vm = vm

        handler!!.postDelayed(Runnable {
            Blurry.with(MurengApplication.appContext)
                .sampling(1)
                .capture(binding.imgBestAnsImage)
                .into(binding.imgBestAnsImage)
        }, 1000)


    }
}


class AnswerUserViewHolder(private val binding: ItemSocialUserAnswerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(diaryData: Diary) {
        binding.diary = diaryData
    }
}

class AnswerBestViewHolder(private val binding: ItemSocialBestAnswerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(diaryData: Diary, vm: BestPopularViewModel) {
        binding.diary = diaryData
        binding.vm = vm
    }
}

object AnswerDiffUtilCallBack : DiffUtil.ItemCallback<Diary>() {
    override fun areItemsTheSame(
            oldItem: Diary,
            newItem: Diary,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
            oldItem: Diary,
            newItem: Diary,
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
enum class AnswerRecyclerType { TYPE_BEST , TYPE_BEST_MORE, TYPE_DETAIL }
