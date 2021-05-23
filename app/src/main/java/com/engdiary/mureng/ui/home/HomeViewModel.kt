package com.engdiary.mureng.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.CheckReplied
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.QuestionRefresh
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    private val _todayQuestion = MutableLiveData<QuestionRefresh>()
    val todayQuestion: LiveData<QuestionRefresh>
        get() = _todayQuestion

    private val _checkReplied = MutableLiveData<CheckReplied>()
    val checkReplied: LiveData<CheckReplied> = _checkReplied

    private var _questionTitle = MutableLiveData<String>()
    var questionTitle : LiveData<String> = _questionTitle

    private val _replyButtonTitle = MutableLiveData<String>()
    val replyButtonTitle : LiveData<String> = _replyButtonTitle

    private var _isReplied = MutableLiveData<Boolean>()
    var isReplied : LiveData<Boolean> = _isReplied

    /** 생성자 */
    init {
        getQuestionData()
        checkReplied()

        _replyButtonTitle.value = "답변하기"
        _questionTitle.value = ""
    }

    fun checkReplied() {
        viewModelScope.launch {
            try {

                _checkReplied.value = murengRepository.getCheckRplied()
//                val replied = _checkReplied.value.replied ?: "2시간 후에 답변을 할 수 있어요"

            } catch (networkError: IOException) {
            }
        }
    }

    fun getQuestionData() {
        viewModelScope.launch {
            try {
                _todayQuestion.value = murengRepository.getTodayQuestionRefresh()

                Log.i("GET!!", "TODATY")

            } catch (networkError: IOException) {
            }
        }
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
