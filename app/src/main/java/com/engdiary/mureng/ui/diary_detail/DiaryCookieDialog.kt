package com.engdiary.mureng.ui.diary_detail

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.engdiary.mureng.databinding.DialogDiaryCookieBinding

class DiaryCookieDialog(context: Context): Dialog(context) {
    private lateinit var binding: DialogDiaryCookieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDiaryCookieBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}