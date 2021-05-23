package com.engdiary.mureng.ui.social_best_more

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityBestMoreBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.social_best.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BestMoreActivity : BaseActivity<ActivityBestMoreBinding>(R.layout.activity_best_more) {

    override val viewModel: BestMoreViewModel by viewModels<BestMoreViewModel>()

    private val answerAdapter: AnswerAdapter by lazy { AnswerAdapter(AnswerRecyclerType.TYPE_BEST_MORE, viewModel, null) }
    private val questionAdapter: QuestionAdapter by lazy { QuestionAdapter(viewModel) }

    private val mode: String?
        get() = intent.getSerializableExtra("mode") as? String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.setMode(mode!!)

        binding.rvSocialBestAnswer.apply {
            adapter = answerAdapter
        }

        binding.rvSocialBestQuestion.apply{
            adapter = questionAdapter
        }

        viewModel.backButton.observe(this, Observer {
            if(it) finish()
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}
