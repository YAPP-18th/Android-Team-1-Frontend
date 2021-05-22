package com.engdiary.mureng.ui.social_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.constant.SortConstant
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import com.engdiary.mureng.ui.social_best.BestPopularViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SocialDetailViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BestPopularViewModel(murengRepository) {

    private var _backButton = MutableLiveData<Boolean>()
    var backButton: LiveData<Boolean> = _backButton

    private var _answerCnt = MutableLiveData<Int>()
    var answerCnt : LiveData<Int> = _answerCnt

    private var _questionTitle = MutableLiveData<String>()
    var questionTitle : LiveData<String> = _questionTitle

    private var _questionContent = MutableLiveData<String>()
    var questionContent : LiveData<String> = _questionContent

    private var _questionUser = MutableLiveData<String>()
    var questionUser : LiveData<String> = _questionUser

    /** 생성자 */
    init {
        _backButton.value = false
        _answerCnt.value = 0

    }

    fun getQuestionData(questionData : QuestionNetwork) {

        _questionContent.value = questionData.contentKr
        _questionTitle.value = questionData.content
        _questionUser.value = ""
        murengRepository.getReplyAnswerList(questionId = questionData.questionId, sort = SortConstant.POP, size = 10, page = 0,
            onSuccess = {
                _ansResults.value = it
                _answerCnt.value = it.size

            },
            onFailure = {
                Timber.d("질문관련 답변 가져오기 실패")
            }
        )
    }

    fun backClick() {
        _backButton.value = true
    }

    override fun questionItemClick(questionData: QuestionNetwork) {
        //TODO("Not yet implemented")
    }

    override fun answerItemClick(answerData: DiaryNetwork) {
        //TODO("Not yet implemented")
    }


    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
