package com.engdiary.mureng.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    private var _selectHome = MutableLiveData<Boolean>()
    var selectHome: LiveData<Boolean> = _selectHome

    private var _selectMyPage = MutableLiveData<Boolean>()
    var selectMyPage: LiveData<Boolean> = _selectMyPage

    private var _selectSocial = MutableLiveData<Boolean>()
    var selectSocial: LiveData<Boolean> = _selectSocial

    private var _selectWriting = MutableLiveData<Boolean>()
    var selectWriting: LiveData<Boolean> = _selectWriting

    var todayQuestion: Question? = null
        private set

    /** 생성자 */
    init {
        _selectHome.value = true
        _selectWriting.value = false
        _selectSocial.value = false
        _selectMyPage.value = false

        setTodayQuestion()
        putUserFcmToken()
    }

    private fun setTodayQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            todayQuestion = murengRepository.getTodayQuestion()
        }
    }

    private fun putUserFcmToken() {
        FirebaseMessaging.getInstance()
            .token
            .addOnSuccessListener {
                putUserFcmTokenToServer(it)
            }
    }

    private fun putUserFcmTokenToServer(fcmToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            murengRepository.putUserFcmToken(fcmToken)
        }
    }

    fun homeClick() {
        if (!_selectHome.value!!) {
            _selectHome.value = true
            _selectMyPage.value = false
            _selectSocial.value = false
            _selectWriting.value = false
        }
    }

    fun MypageClick() {
        if (!_selectMyPage.value!!) {
            _selectHome.value = false
            _selectMyPage.value = true
            _selectSocial.value = false
            _selectWriting.value = false
        }
    }

    fun WritingClick() {
        _selectWriting.value = true
    }

    fun SocialClick() {
        if (!_selectSocial.value!!) {
            _selectHome.value = false
            _selectMyPage.value = false
            _selectSocial.value = true
            _selectWriting.value = false
        }
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
