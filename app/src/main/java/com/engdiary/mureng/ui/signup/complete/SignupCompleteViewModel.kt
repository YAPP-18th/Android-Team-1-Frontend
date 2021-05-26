package com.engdiary.mureng.ui.signup.complete

import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import javax.inject.Inject

class SingupCompleteViewModel @Inject constructor(
        private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    init {
    }

    override fun onCleared() {
        super.onCleared()
    }
}