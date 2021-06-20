package com.engdiary.mureng.ui.my

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

const val RECORD_TAB_INDEX = 0
const val AWARD_TAB_INDEX = 1
const val SCRAP_TAB_INDEX = 2

class MyPageViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        RECORD_TAB_INDEX to { RecordFragment() },
        AWARD_TAB_INDEX to { AwardFragment() },
        SCRAP_TAB_INDEX to { ScrapFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
