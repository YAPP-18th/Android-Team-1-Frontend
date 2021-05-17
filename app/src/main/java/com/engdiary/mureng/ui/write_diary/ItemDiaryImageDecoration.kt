package com.engdiary.mureng.ui.write_diary

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDiaryImageDecoration(
    private val spanCount: Int,
    private val spacing: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.top = spacing
        outRect.left = getLeftOffset(spacing, column)
        outRect.right = getRightOffset(spacing, column)
    }

    private fun getLeftOffset(spacing: Int, column: Int): Int {
        return spacing - column * spacing / spanCount
    }

    private fun getRightOffset(spacing: Int, column: Int): Int {
        return (column + 1) * spacing / spanCount
    }
}
