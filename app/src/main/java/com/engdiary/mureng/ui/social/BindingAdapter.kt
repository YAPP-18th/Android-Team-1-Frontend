package com.engdiary.mureng.ui.social

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


object BindingAdapter {


    @BindingAdapter("setTapContents", "setVm")
    @JvmStatic
    fun setTapContents(tabLayout: TabLayout, items: List<String>?, mainVm: SocialViewModel?) {
        items?.forEach {
            with(tabLayout) {
                newTab().let { tab ->
                    tab.text = it
                    addTab(tab)
                }
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        //Nothing.
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        //Nothing.
                    }

                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        tab?.position?.let { position ->
                            mainVm?.selectPosition(position)
                        }
                    }
                })
            }
        }
    }

    @BindingAdapter("setPagerCount", "setFsm", "setVm")
    @JvmStatic
    fun setViewPager(
        viewPager: ViewPager,
        items: List<String>?,
        fragmentManager: FragmentManager?,
        mainVm: SocialViewModel?
    ) {
        if (!items.isNullOrEmpty())
            viewPager.adapter?.run {
                if (this is ViewPagerAdapter) {
                    setItems(items)
                }
            } ?: kotlin.run {
                if (fragmentManager != null)
                    viewPager.adapter = ViewPagerAdapter(fragmentManager, items)
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                        //Nothing.
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        //Nothing.
                    }

                    override fun onPageSelected(position: Int) {
                        mainVm?.selectPosition(position)
                    }
                })
            }
    }

    @BindingAdapter("setViewPosition")
    @JvmStatic
    fun setViewPosition(view: View, position: Int?) {
        if (position != null)
            when (view) {
                is ViewPager -> {
                    view.currentItem = position
                }
                is TabLayout -> {
                    view.run {
                        getTabAt(position)?.let { tab ->
                            selectTab(tab)
                        }

                    }
                }
            }
    }

}