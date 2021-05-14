package com.engdiary.mureng.ui.diary_detail

import android.os.Bundle
import androidx.activity.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityDiaryDetailBinding
import com.engdiary.mureng.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryDetailActivity() : BaseActivity<ActivityDiaryDetailBinding>(R.layout.activity_diary_detail) {
    override val viewModel: DiaryDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)


    }
}
