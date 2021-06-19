package com.engdiary.mureng.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.CheckReplied
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.QuestionRefresh
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {


//    private val _tabItems: MutableLiveData<List<String>> = MutableLiveData()

//    private val _tabItems: MutableLiveData<List<String>> = MutableLiveData()
//    private val _position: MutableLiveData<Int> = MutableLiveData()
//    val tabItems: LiveData<List<String>> get() = _tabItems

//    private val _todayQuestion = MutableLiveData<List<QuestionRefresh>>()
//    val todayQuestion: LiveData<QuestionRefresh>
//        get() = _todayQuestion

private val _todayQuestion = MutableLiveData<QuestionRefresh>()
    val todayQuestion: LiveData<QuestionRefresh>
        get() = _todayQuestion

    protected val _quesResults = MutableLiveData<List<QuestionNetwork>>(listOf())
    open var quesResults: LiveData<List<QuestionNetwork>> = _quesResults

    private val _todayExpression = MutableLiveData<List<TodayExpression>>(listOf())
    val todayExpression: LiveData<List<TodayExpression>> = _todayExpression


//    open val ansResults: LiveData<List<DiaryNetwork>> = _ansResults


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

                _todayExpression.value = murengRepository.getTodayExpression()

                Log.i("HEARARER", "QEWRQWERE")

            } catch (networkError: IOException) {
            }
        }
    }

//    fun getMyQuesList() {
//        murengRepository.getMyQuestionList(
//            onSuccess = {
//                _quesResults.value = it
//                _quesCnt.value = it.size
//            },
//            onFailure = {
//                Timber.e("나의 질문 리스트 가져오기 통신 실패")
//            }
//        )
//    }



    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
