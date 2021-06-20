package com.engdiary.mureng.ui.signup.nickname

import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.NickName
import com.engdiary.mureng.data.SingleLiveEvent
import com.engdiary.mureng.data.request.PostSignupRequest
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import com.kakao.usermgmt.StringSet.email
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class SignupNickNameViewModel @Inject constructor(
        private val murengRepository: MurengRepository,
        private val authManager: AuthManager
): BaseViewModel(murengRepository) {



    private var _isDuplicate = MutableLiveData<Boolean>()
    var isDuplicate: LiveData<Boolean> = _isDuplicate

    private var _next = MutableLiveData<String>()
    var next: LiveData<String> = _next

    private val _navigateToSignupComplete = SingleLiveEvent<Unit>()
    val navigateToSignupComplete: LiveData<Unit>
        get() = _navigateToSignupComplete

    private val _signupFail = SingleLiveEvent<Unit>()
    val signupFail: LiveData<Unit>
        get() = _signupFail

    var nickName = ""

    init {
        _isDuplicate.value = true
        _next.value = "다음"
    }

    fun userSignup() {
        if(_isDuplicate.value == false) {

            val currentTimestamp = System.currentTimeMillis()
            val signupRequest = PostSignupRequest(identifier = authManager.identifier, nickname = nickName, email = null)
//
            viewModelScope.launch {
                murengRepository.userSignup(
                    signupRequest,
                    successAction = {
                        _navigateToSignupComplete.call()
                    },
                    failAction = {
                        _signupFail.call()
                    }
                )
            }

        }
    }

    fun onTextChanged(s: CharSequence, start :Int, before : Int, count: Int) {
        viewModelScope.launch {

            nickName = String(s.toString().toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
            val res = murengRepository.getNickNameExist(nickName)
            _isDuplicate.value = res!!.duplicated
            if(res!!.duplicated) {
                _next.value = "다음"
            } else {
                _next.value = "완료"
            }

        }

    }

    override fun onCleared() {
        super.onCleared()
    }

}