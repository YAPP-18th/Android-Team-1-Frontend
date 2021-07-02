package com.engdiary.mureng.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.*
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : ScrapViewModel(murengRepository) {

    private val _checkReplied = MutableLiveData<CheckReplied>()
    val checkReplied: LiveData<CheckReplied> = _checkReplied

    private var _questionTitle = MutableLiveData<String>()
    var questionTitle : LiveData<String> = _questionTitle

    private var _question = MutableLiveData<Question>()
    var question : LiveData<Question> = _question
        get() = _question

    private var _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName


    /** 생성자 */
    init {
        _userName.value = ""
        _questionTitle.value = ""

        viewModelScope.launch {
            val todayQuestion = murengRepository.getTodayQuestion()
            todayQuestion?.let {
                if(it != null) {
                    _question.value = it
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            murengRepository.getMyInfo()
                    ?.let { _userName.postValue(it.nickname) }

            murengRepository.getCheckReplied()?.let {
                _checkReplied.postValue(it)
            }

            murengRepository.getTodayExpression()
                    .let {
                        if(it != null) {
                            _todayExpression.postValue(it)
                        }
                    }
            _questionTitle.postValue(murengRepository.getTodayQuestion()?.content)

        }


    }

    fun getQuestionData() {
        viewModelScope.launch {
            try {
                _questionTitle.postValue(murengRepository.getTodayQuestionRefresh()?.content)

            } catch (networkError: IOException) {
            }
        }
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
