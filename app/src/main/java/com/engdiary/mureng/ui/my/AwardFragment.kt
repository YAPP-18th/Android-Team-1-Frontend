package com.engdiary.mureng.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.data.domain.Record
import com.engdiary.mureng.databinding.BestTabFragmentBinding
import com.engdiary.mureng.databinding.FragmentAwardBinding
import com.engdiary.mureng.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AwardFragment : BaseFragment<FragmentAwardBinding>(R.layout.fragment_award) {

    override val viewModel: AwardViewModel by viewModels<AwardViewModel>()
    private val murengTreyAdapter: MurengTreyAdapter by lazy { MurengTreyAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.rvAwardTrey.apply {
            adapter = murengTreyAdapter
        }

        subscribeUi()

    }

    private fun subscribeUi() {
        val murengTrey : MutableList<Record> = mutableListOf()
        viewModel.userAward.observe(viewLifecycleOwner){

            if(it!!.member.murengCount != 0) {
                for (i in 1 .. it.member.murengCount) {
                    when(i % 8) {
                        0 -> murengTrey.add(Record(R.drawable.mureng_8))
                        1 -> murengTrey.add(Record(R.drawable.mureng_1))
                        2 -> murengTrey.add(Record(R.drawable.mureng_2))
                        3 -> murengTrey.add(Record(R.drawable.mureng_3))
                        4 -> murengTrey.add(Record(R.drawable.mureng_4))
                        5 -> murengTrey.add(Record(R.drawable.mureng_5))
                        6 -> murengTrey.add(Record(R.drawable.mureng_6))
                        7 -> murengTrey.add(Record(R.drawable.mureng_7))
                    }
                }
            }
            murengTreyAdapter.submitList(murengTrey)
        }
    }




    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}


