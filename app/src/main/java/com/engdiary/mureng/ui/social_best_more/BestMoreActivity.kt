package com.engdiary.mureng.ui.social_best_more

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.BestMoreConstant
import com.engdiary.mureng.databinding.ActivityBestMoreBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.social_best.*
import com.engdiary.mureng.util.EndlessScrollListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BestMoreActivity : BaseActivity<ActivityBestMoreBinding>(R.layout.activity_best_more) {

    override val viewModel: BestMoreViewModel by viewModels<BestMoreViewModel>()

    private val answerAdapter: AnswerAdapter by lazy { AnswerAdapter(
        AnswerRecyclerType.TYPE_BEST_MORE,
        viewModel
    ) }
    private val questionAdapter: QuestionAdapter by lazy { QuestionAdapter(QuestionRecyclerType.TYPE_QUES_MORE,viewModel) }

    private var scrollListener: EndlessScrollListener? = null

    private val mode: String?
        get() = intent.getSerializableExtra("mode") as? String

    private var page : Int = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        scrollListener?.reset()

        viewModel.setMode(mode!!)
        viewModel.clickedSort.observe(this, Observer {
            if (!it) {
                page = 0
            }
        })

        binding.apply {

            nsvBestMore.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
                if (v.getChildAt(v.childCount - 1) != null) {
                    if (scrollY >= v.getChildAt(v.childCount - 1)
                            .measuredHeight - v.measuredHeight &&
                        scrollY > oldScrollY
                    ) {
                        if (mode == BestMoreConstant.ANS) {
                            if(page <= viewModel.totalPage.value!!) {
                                viewModel.getAnswerData(page++)
                            }
                        } else {
                            if(page <= viewModel.totalPage.value!!) {
                                viewModel.getQuestionData(page++)
                            }
                        }
                    }
                }
            } )


            rvSocialBestAnswer.apply {
                adapter = answerAdapter
            }
                

            rvSocialBestQuestion.apply {
                adapter = questionAdapter
            }
        }

        binding.imgBestMoreBack.setOnClickListener { finish() }

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}
