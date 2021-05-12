package com.engdiary.mureng.ui.social

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(fragmentManager: FragmentManager, private val titles: List<CharSequence>, private val conetent: List<Fragment>)
    : FragmentStatePagerAdapter(fragmentManager) {

    private val numOfTabs: Int = titles.size

    private val PAGE1 = 0
    private val PAGE2 = 1

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            PAGE1 -> fragment = conetent[0]
            PAGE2 -> fragment = conetent[1]
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return numOfTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}