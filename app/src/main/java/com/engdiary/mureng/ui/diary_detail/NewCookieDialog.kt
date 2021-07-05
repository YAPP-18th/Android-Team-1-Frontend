package com.engdiary.mureng.ui.diary_detail

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.engdiary.mureng.databinding.DialogNewCookieBinding
import com.engdiary.mureng.util.setRound

class NewCookieDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogNewCookieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogNewCookieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textviewDiarycookieAccept.setOnClickListener { this.dismiss() }
        binding.imageviewDiarycookieBg.setRound(BACKGROUND_ROUND)
        binding.root.setRound(BACKGROUND_ROUND)
    }

    companion object{
        private const val BACKGROUND_ROUND = 16f
    }
}
