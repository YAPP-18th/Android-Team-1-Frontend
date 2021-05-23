package com.engdiary.mureng.ui.social_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.SortConstant
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
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

    private var _clickedSort = MutableLiveData<Boolean>()
    var clickedSort : LiveData<Boolean> = _clickedSort

    private var _selectedSort = MutableLiveData<String>()
    var selectedSort : LiveData<String> = _selectedSort

    private var _questionNetwork = MutableLiveData<QuestionNetwork>()
    var questionNetwork : LiveData<QuestionNetwork> = _questionNetwork

    private var _isPop = MutableLiveData<Boolean>()
    var isPop : LiveData<Boolean> = _isPop

    /** 생성자 */
    init {
        _backButton.value = false
        _answerCnt.value = 0
        _clickedSort.value = false

    }

    fun getQuestionData(questionData : QuestionNetwork) {
        _questionNetwork.value = questionData
        _questionContent.value = questionData.contentKr
        _questionTitle.value = questionData.content
        _questionUser.value = ""
        murengRepository.getReplyAnswerList(questionId = questionData.questionId, sort = SortConstant.POP, size = 10, page = 0,
            onSuccess = {
                _ansResults.value = it
                _answerCnt.value = it.size
                _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
                _isPop.value = true
            },
            onFailure = {
                Timber.d("질문관련 답변 가져오기 실패")
            }
        )
    }

    fun sortClick() {
        _clickedSort.value = !_clickedSort.value!!
    }

    fun menuClick() {
        if (!_isPop.value!!) {
            murengRepository.getReplyAnswerList(questionId = _questionNetwork.value!!.questionId, sort = SortConstant.POP, size = 10, page = 0,
                    onSuccess = {
                        _ansResults.value = it
                        _answerCnt.value = it.size
                        _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
                        _clickedSort.value = false
                        _isPop.value = true
                    },
                    onFailure = {
                        Timber.d("질문관련 답변 가져오기 실패")
                    }
            )
        } else {
            murengRepository.getReplyAnswerList(questionId = _questionNetwork.value!!.questionId, sort = SortConstant.NEW, size = 10, page = 0,
                    onSuccess = {
                        _ansResults.value = it
                        _answerCnt.value = it.size
                        _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.newest)
                        _clickedSort.value = false
                        _isPop.value = false
                    },
                    onFailure = {
                        Timber.d("질문관련 답변 가져오기 실패")
                    }
            )
        }

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
