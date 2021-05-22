package com.engdiary.mureng.ui.social_best

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.databinding.ItemSocialAnswerBinding
import com.engdiary.mureng.databinding.ItemSocialBestAnswerBinding
import com.engdiary.mureng.databinding.ItemSocialUserAnswerBinding


/**
 * Social_Best Tab 인기 답변 RecyclerView Adapter
 */
class AnswerAdapter(val type: AnswerRecyclerType, val vm: BestPopularViewModel) :
    ListAdapter<DiaryNetwork, RecyclerView.ViewHolder>(AnswerDiffUtilCallBack) {
    companion object {
        const val TYPE_BEST = 0
        const val TYPE_BEST_MORE = 1
        const val TYPE_DETAIL = 2
    }

    override fun getItemViewType(position: Int): Int {
        when (type) {
            AnswerRecyclerType.TYPE_BEST -> {
                return TYPE_BEST
            }
            AnswerRecyclerType.TYPE_BEST_MORE -> {
                return TYPE_BEST_MORE
            }
            else -> {
                return TYPE_DETAIL
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
                AnswerViewHolder(binding)
            }
            TYPE_BEST_MORE -> {
                val binding: ItemSocialBestAnswerBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.item_social_best_answer, parent, false
                )
                AnswerBestViewHolder(binding)
            }
            else -> {
                val binding: ItemSocialUserAnswerBinding = DataBindingUtil.inflate(
                        layoutInflater, R.layout.item_social_user_answer, parent, false
                )
                AnswerUserViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_BEST -> {
                (holder as AnswerViewHolder).bind(getItem(position), vm)
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

    fun bind(diaryData: DiaryNetwork, vm: BestPopularViewModel) {
        binding.diary = diaryData
        binding.vm = vm

//        val paint = Paint()
//        val imageBitmap = binding.imgBestAnsImage.drawable!!.toBitmap()
//        val filterBitmap = binding.imgBestAnsFilter.drawable!!.toBitmap()
//        val canvas : Canvas = Canvas(imageBitmap)
//        val mode: PorterDuff.Mode =  PorterDuff.Mode.OVERLAY
//        paint.xfermode = PorterDuffXfermode(mode)
//        canvas.drawBitmap(filterBitmap, 0f, 0f, paint)
//
//        binding.imgBestAnsImage.setImageBitmap(imageBitmap)



    }
}

class AnswerUserViewHolder(private val binding: ItemSocialUserAnswerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(diaryData: DiaryNetwork) {
        binding.diary = diaryData
    }
}

class AnswerBestViewHolder(private val binding: ItemSocialBestAnswerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(diaryData: DiaryNetwork, vm: BestPopularViewModel) {
        binding.diary = diaryData
        binding.vm = vm
    }
}

object AnswerDiffUtilCallBack : DiffUtil.ItemCallback<DiaryNetwork>() {
    override fun areItemsTheSame(
            oldItem: DiaryNetwork,
            newItem: DiaryNetwork,
    ): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(
            oldItem: DiaryNetwork,
            newItem: DiaryNetwork,
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
enum class AnswerRecyclerType { TYPE_BEST , TYPE_BEST_MORE, TYPE_DETAIL }
