package com.engdiary.mureng.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * RecyclerView paging 처리를 위한 클래스
 */
abstract class EndlessScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val visibleThreshold: Int = 0
) : RecyclerView.OnScrollListener() {
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        val totalItemCount = layoutManager.itemCount

        val lastVisibleItemPosition = when (layoutManager) {
            is GridLayoutManager -> layoutManager.findLastCompletelyVisibleItemPosition()
            is LinearLayoutManager -> layoutManager.findLastCompletelyVisibleItemPosition()
            else -> 0
        }

        Timber.e("previousTotalItemCount ${totalItemCount}")
        Timber.e("last ${lastVisibleItemPosition}")

        if(!recyclerView.canScrollVertically(1)) {
            Timber.e("end??")
        }


        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold >= totalItemCount) {
            currentPage++
            onLoadMore(currentPage)
            loading = true
        }
    }

    fun reset(){
        currentPage = 0
        previousTotalItemCount = 0
    }

    abstract fun onLoadMore(page: Int)
}
