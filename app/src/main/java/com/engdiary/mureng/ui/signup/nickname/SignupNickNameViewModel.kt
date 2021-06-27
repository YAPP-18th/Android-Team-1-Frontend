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
import kotlinx.coroutines.async
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
) : BaseViewModel(murengRepository) {


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


    init {
        _isDuplicate.value = true
        _next.value = "다음"
    }

    fun requestSignUp(nickname: String){
        viewModelScope.launch {
            val completableDeferred = viewModelScope.async {
                getNicknameExist(nickname)
            }
            completableDeferred.await()
            postSignUp(nickname)
        }
    }

    private suspend fun getNicknameExist(nickname: String) {
            if(nickname.isNotBlank()) {
                val res = murengRepository.getNickNameExist(nickname)
                _isDuplicate.value = res?.duplicated
                if(res?.duplicated!!){
                    _next.value = "다음"
                } else{
                    _next.value = "완료"
                }
            }
            else {
                _isDuplicate.value = true
                _next.value = "다음"
            }

    }

    private fun postSignUp(nickname: String) {
        if (_isDuplicate.value == false) {

            val signupRequest = PostSignupRequest(
                identifier = authManager.identifier,
                nickname = nickname,
                email = null
            )

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



    override fun onCleared() {
        super.onCleared()
    }
}
