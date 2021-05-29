package com.engdiary.mureng.ui.login

import android.util.Log
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

    fun kakaoLogin() {
        addKakaoUser(
            accessToken = "F2Pr3eQEfsYODBjRtckMAoaqszE37hTrp47kZgo9dBEAAAF5rdy6Gg",
            refreshToken = "ZLgZuH0IU1WcoJJlc4lCCBC9_sykb6D4v-YUFwo9dBEAAAF5rdy6GQ",
            userId = 234234)

//        murengRepository.settingUser()
        Log.i("KAKAO!!!!!", "KAKAKAKA")

    }

}