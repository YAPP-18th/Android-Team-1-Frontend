package com.engdiary.mureng.ui.social_myques

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engdiary.mureng.data.BestAnswerData
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import com.engdiary.mureng.ui.social_best.PopularViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyQuesViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : PopularViewModel(murengRepository) {

    private var _quesCnt = MutableLiveData<Int>()
    var quesCnt: LiveData<Int> = _quesCnt

    /** 생성자 */
    init {
        _quesCnt.value = 0
        getMyQuesList()
    }

    fun getMyQuesList() {
        murengRepository.getMyQuestionList(
            onSuccess = {
                _quesResults.value = it
                _quesCnt.value = it.size
            },
            onFailure = {
                Timber.e("나의 질문 리스트 가져오기 통신 실패")
            }
        )
    }

    override fun questionItemClick(questionData: QuestionNetwork) {
    }

    override fun answerItemClick(answerData: BestAnswerData) {
        // TODO 안쓰임
    }


    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
