package com.engdiary.mureng.ui.setting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.constant.IntentKey.INSTA_GRAM
import com.engdiary.mureng.constant.IntentKey.OPEN_SOURCE
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel

class WebviewViewModel @ViewModelInject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    private val _barTitle = MutableLiveData<String>()
    val barTitle : LiveData<String> = _barTitle


    init {

    }

    fun setMode(mode: String) {
        if(mode == OPEN_SOURCE) {
            _barTitle.value = "오픈소스 라이센스"
        } else if (mode == INSTA_GRAM) {
            _barTitle.value = "머렝 인스타그램"
        }
    }

}
