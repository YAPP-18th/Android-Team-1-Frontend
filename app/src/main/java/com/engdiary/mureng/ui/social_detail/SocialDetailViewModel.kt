package com.engdiary.mureng.ui.social_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.data.AnswerData
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SocialDetailViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : UserAnswerViewModel(murengRepository) {

    private var _backButton = MutableLiveData<Boolean>()
    var backButton: LiveData<Boolean> = _backButton

    private var _questionData: MutableLiveData<QuestionData> = MutableLiveData()
    var questionData: LiveData<QuestionData> = _questionData

    private var _answerCnt = MutableLiveData<Int>()
    var answerCnt : LiveData<Int> = _answerCnt

    /** 생성자 */
    init {
        _backButton.value = false
        _answerCnt.value = 0

    }

    fun backClick() {
        _backButton.value = true
    }

    fun getQuesData(questionData: QuestionData) {
        _questionData.value = questionData
    }

    override fun answerItemClick(answerData: AnswerData) {
        TODO("Not yet implemented")
    }


    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
