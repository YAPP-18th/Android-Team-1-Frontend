package com.engdiary.mureng.ui.write_diary

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityWritingDiaryImageBinding

class WritingDiaryImageActivity : AppCompatActivity() {
    private val viewModel by viewModels<WritingDiaryImageViewModel>()
    private lateinit var binding: ActivityWritingDiaryImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_writing_diary_image)
        binding.lifecycleOwner = this
        initButtons(binding.buttonWritingdiaryimageBack, binding.textviewWritingdiaryWrite)
    }

    private fun initButtons(
        imageButtonWritingdiaryCancel: ImageButton,
        textviewWritingdiaryWrite: TextView
    ) {
        imageButtonWritingdiaryCancel.setOnClickListener { finish() }
        textviewWritingdiaryWrite.setOnClickListener {
            // todo 서버 통신하기
        }
    }
}