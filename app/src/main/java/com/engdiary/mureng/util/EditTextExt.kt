package com.engdiary.mureng.util

import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter


@BindingAdapter("textChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}