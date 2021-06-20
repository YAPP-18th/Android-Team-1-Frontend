package com.engdiary.mureng.ui.signup.complete

import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingupCompleteViewModel @Inject constructor(
        private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    init {
    }

    override fun onCleared() {
        super.onCleared()
    }
}