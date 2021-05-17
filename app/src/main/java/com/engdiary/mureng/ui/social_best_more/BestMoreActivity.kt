package com.engdiary.mureng.ui.social_best_more

import android.os.Bundle
import androidx.activity.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.databinding.ActivityBestMoreBinding
import com.engdiary.mureng.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BestMoreActivity : BaseActivity<ActivityBestMoreBinding>(R.layout.activity_best_more) {

    override val viewModel: BestMoreViewModel by viewModels<BestMoreViewModel>()


    private val questionData: QuestionData?
        get() = intent.getSerializableExtra("questionData") as? QuestionData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

//        questionData?.let { viewModel.getQuesData(it) } ?: finish()

//        viewModel.backButton.observe(this, Observer {
//            if(it) {
//                // TODO Back Button 기능 구현
//            }
//        })

    }
}
