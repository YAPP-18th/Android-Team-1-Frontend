package com.engdiary.mureng.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.MyPageFragmentBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.setting.SettingActivity
import com.engdiary.mureng.util.startActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<MyPageFragmentBinding>(R.layout.my_page_fragment) {

    override val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.apply {
            vm = viewModel
            lifecycleOwner = this@MyPageFragment.viewLifecycleOwner
        }

        initTabBar()
        initUi()
    }

    private fun initTabBar() {
        binding.mypageViewpager.adapter = MyPageViewPagerAdapter(requireActivity())
        binding.tabMypageBar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.mypageViewpager.currentItem = tab?.position ?: RECORD_TAB_INDEX
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        TabLayoutMediator(binding.tabMypageBar, binding.mypageViewpager) { tab, position ->
            tab.text = getTabText(position)
        }.attach()
    }

    private fun getTabText(position: Int): String? {
        return when (position) {
            RECORD_TAB_INDEX -> getString(R.string.mypage_tabitem_record)
            AWARD_TAB_INDEX -> getString(R.string.mypage_tabitem_award)
            SCRAP_TAB_INDEX -> getString(R.string.mypage_tabitem_scrap)
            else -> null
        }
    }

    private fun initUi() {
        binding.mypageNavigatetoSetting.setOnClickListener {
            startActivity(SettingActivity::class)
        }
    }
}
