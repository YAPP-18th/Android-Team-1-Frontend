package com.engdiary.mureng.ui.my

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.data.Badge
import com.engdiary.mureng.data.Record
import com.engdiary.mureng.databinding.BestTabFragmentBinding
import com.engdiary.mureng.databinding.FragmentAwardBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.social_best.AnswerAdapter
import com.engdiary.mureng.ui.social_best.AnswerRecyclerType
import com.engdiary.mureng.ui.social_best.BestTabViewModel
import com.engdiary.mureng.ui.social_best.QuestionAdapter
import com.engdiary.mureng.util.HorizontalItemDecorator
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
                for (i in 0 .. it.member.murengCount) {
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


