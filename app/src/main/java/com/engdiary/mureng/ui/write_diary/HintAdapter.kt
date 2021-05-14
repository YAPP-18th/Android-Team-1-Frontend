package com.engdiary.mureng.ui.write_diary

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.data.Hint

class HintAdapter(private val hints: List<Hint>) : RecyclerView.Adapter<HintViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        return HintViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        holder.bind(hints[position])
    }

    override fun getItemCount(): Int = hints.size
}
