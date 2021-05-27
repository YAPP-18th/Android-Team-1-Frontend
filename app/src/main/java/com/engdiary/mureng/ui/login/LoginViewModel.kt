package com.engdiary.mureng.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
        private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    /** 생성자 */
    init {

    }

    override fun onCleared() {
        super.onCleared()
    }
}