package com.engdiary.mureng.ui.my

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.FragmentScrapBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.home.TodayExpressionAdapter
import com.engdiary.mureng.ui.write_diary.HintDecoration
import com.engdiary.mureng.util.dpToPx
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScrapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ScrapFragment : BaseFragment<FragmentScrapBinding>(R.layout.fragment_scrap) {
    override val viewModel: MyScrapViewModel by viewModels<MyScrapViewModel>()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.vm = viewModel

        viewModel.todayExpression.observe(viewLifecycleOwner, Observer { expressions ->
            expressions?.let { initHintAdapter(binding.homeTodayExpression, TodayExpressionAdapter(it, viewModel)) }
        })

    }

    private fun initHintAdapter(
            hints: RecyclerView,
            hintAdapter: TodayExpressionAdapter
    ) {
        hints.adapter = hintAdapter
        hints.addItemDecoration(HintDecoration(ScrapFragment.HINT_SPAN_COUNT, ScrapFragment.HINT_SPACING.dpToPx()))
    }

    companion object {
        private const val HINT_SPAN_COUNT = 10
        private const val HINT_SPACING = 20
    }


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_scrap, container, false)
//    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ScrapFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ScrapFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}