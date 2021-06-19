package com.engdiary.mureng.ui.home

import android.graphics.Color
import android.net.wifi.hotspot2.pps.HomeSp
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.HomeFragmentBinding
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    override val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    val handler : Handler = Handler()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.reply.setBackgroundColor(Color.BLACK)

        binding.apply {
            vm = viewModel
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
        }
        viewModel.todayQuestion.observe(viewLifecycleOwner, Observer {
            binding.todayQuestion.text = it.content
        })

        viewModel.checkReplied.observe(viewLifecycleOwner, Observer {
            if(it.replied == true) {
                binding.reply.text = "2시간 후에 답변을 할 수 있어요"
            } else {
                binding.reply.text = "답변하기"
            }
        })



    }

}