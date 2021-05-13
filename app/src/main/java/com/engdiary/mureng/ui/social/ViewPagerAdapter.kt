package com.engdiary.mureng.ui.social

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.engdiary.mureng.ui.social_best.BestTabFragment
import com.engdiary.mureng.ui.social_myques.MyQuesFragment

class ViewPagerAdpater(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BestTabFragment()
            else -> MyQuesFragment()
        }
    }

    override fun getCount() = 2
}