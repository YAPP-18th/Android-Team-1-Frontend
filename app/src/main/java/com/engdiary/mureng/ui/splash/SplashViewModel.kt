package com.engdiary.mureng.ui.splash


import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
        private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    /** 생성자 */
    init {

    }

    override fun onCleared() {
        super.onCleared()
    }
}