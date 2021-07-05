package com.engdiary.mureng.ui.social_detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.data.domain.Question
import com.engdiary.mureng.databinding.ActivitySocialDetailBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.social_best.AnswerAdapter
import com.engdiary.mureng.ui.social_best.AnswerRecyclerType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialDetailActivity :
    BaseActivity<ActivitySocialDetailBinding>(R.layout.activity_social_detail) {

    override val viewModel: SocialDetailViewModel by viewModels<SocialDetailViewModel>()
    private val answerAdapter: AnswerAdapter by lazy {
        AnswerAdapter(
            AnswerRecyclerType.TYPE_DETAIL,
            viewModel
        )
    }

    /** 이전 화면에서부터 받은 QuestionItem[QuestionData] */
    private val questionData: Question?
        get() = intent.getParcelableExtra(IntentKey.QUESTION) as? Question

    private var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        questionData?.let { viewModel.getQuestionData(it) } ?: finish()

        binding.nsvSocialDetail.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight &&
                    scrollY > oldScrollY
                ) {
                    if (page <= viewModel.totalPage.value!!) {
                        viewModel.getPagingReplyData(page++)
                    }
                }
            }
        })

        binding.rvSocialMyques.apply {
            adapter = answerAdapter
        }

        binding.imgSocialDetailBack.setOnClickListener { finish() }
    }
}