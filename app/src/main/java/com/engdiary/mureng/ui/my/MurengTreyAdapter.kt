package com.engdiary.mureng.ui.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.R
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.Record
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.databinding.ItemBadgesBinding
import com.engdiary.mureng.databinding.ItemSocialQuesBinding
import com.engdiary.mureng.ui.social_best.BestPopularViewModel
import com.engdiary.mureng.util.setOnSingleClickListener

class MurengTreyAdapter() :
    ListAdapter<Record, MurengTreyViewHolder>(MurengTreyDiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MurengTreyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBadgesBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_badges, parent, false)

        return MurengTreyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MurengTreyViewHolder, position: Int) {
        holder.bind(getItem(position))

    }
}

class MurengTreyViewHolder(private val binding: ItemBadgesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(record: Record) {
        binding.record = record

    }
}

object MurengTreyDiffUtilCallBack : DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(
        oldItem: Record,
        newItem: Record
    ): Boolean {
        return oldItem.Image == newItem.Image
    }

    override fun areContentsTheSame(
        oldItem: Record,
        newItem: Record
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}

