package com.engdiary.mureng.ui.signup.nickname

import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.NickName
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class SignupNickNameViewModel @Inject constructor(
        private val murengRepository: MurengRepository
): BaseViewModel(murengRepository) {

    private var _isDuplicate = MutableLiveData<Boolean>()
    var isDuplicate: LiveData<Boolean> = _isDuplicate

    private var _next = MutableLiveData<String>()
    var next: LiveData<String> = _next

    init {
        _isDuplicate.value = true
        _next.value = "다음"
    }

    fun onTextChanged(s: CharSequence, start :Int, before : Int, count: Int) {
        viewModelScope.launch {

            val res = murengRepository.getNickNameExist(s.toString())
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