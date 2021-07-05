package com.engdiary.mureng.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityDevelopPersonBinding
import com.engdiary.mureng.databinding.ActivitySettingBinding

class DevelopPersonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDevelopPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgDevelopBack.setOnClickListener { finish() }
    }
}