package com.engdiary.mureng.ui.social_detail

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.constant.SortConstant
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import com.engdiary.mureng.ui.social_best.BestPopularViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SocialDetailViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BestPopularViewModel(murengRepository) {

    private var _backButton = MutableLiveData<Boolean>()
    val backButton: LiveData<Boolean> = _backButton

    private var _answerCnt = MutableLiveData<Int>()
    val answerCnt : LiveData<Int> = _answerCnt

    private var _questionTitle = MutableLiveData<String>()
    val questionTitle : LiveData<String> = _questionTitle

    private var _questionContent = MutableLiveData<String>()
    val questionContent : LiveData<String> = _questionContent

    private var _questionUser = MutableLiveData<String>()
    val questionUser : LiveData<String> = _questionUser

    private var _clickedSort = MutableLiveData<Boolean>()
    val clickedSort : LiveData<Boolean> = _clickedSort

    private var _selectedSort = MutableLiveData<String>()
    val selectedSort : LiveData<String> = _selectedSort

    private var _questionNetwork = MutableLiveData<QuestionNetwork>()
    val questionNetwork : LiveData<QuestionNetwork> = _questionNetwork

    private var _isPop = MutableLiveData<Boolean>()
    val isPop : LiveData<Boolean> = _isPop

    private var _questionUserImg = MutableLiveData<String>()
    val questionUserImg : LiveData<String> = _questionUserImg

    private var _totalPage = MutableLiveData<Int>()
    val totalPage : LiveData<Int> = _totalPage

    private var _qusetionId = MutableLiveData<Int>()
    val qusetionId : LiveData<Int> = _qusetionId

    /** 생성자 */
    init {
        _backButton.value = false
        _answerCnt.value = 0
        _clickedSort.value = false
        _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
        _isPop.value = true


    }

    fun getQuestionData(questionData : QuestionNetwork) {
        _questionNetwork.value = questionData
        _questionContent.value = questionData.contentKr
        _questionTitle.value = questionData.content
        _qusetionId.value = questionData.questionId
        if(questionData.author != null) {
            _questionUser.value = questionData.author.nickname
            if(questionData.author.image != null) {
                _questionUserImg.value = questionData.author.image
            }
        }
        getPagingReplyData(0)
    }

    fun getPagingReplyData(page : Int) {
        var sort : String? = null
        sort = if(_isPop.value!!) {
            SortConstant.POP
        } else {
            SortConstant.NEW
        }
        murengRepository.getReplyAnswerList(questionId = _qusetionId.value!!, sort = sort, size = 10, page = page,
            onSuccess = {
                if(page > 0) {
                    for (item in it.data!!) {
                        addAnswerResult(item)
                    }
                } else {
                    _ansResults.value = it.data!!
                    _answerCnt.value = it.totalItemSize!!
                    _totalPage.value = it.totalPage!!
                }
            },
            onFailure = {
                Timber.d("질문관련 답변 가져오기 실패")
            }
        )
    }

    override fun answerItemHeartClick(answerData: DiaryNetwork) {
        if (answerData.likeYn) {
            deleteLike(answerData.id)
        } else {
            addLike(answerData.id)
        }
    }

    fun deleteLike(replyId : Int) {
        murengRepository.deleteLikes(replyId,
            onSuccess = {
                Timber.d("좋아요 삭제 성공")
            },
            onFailure = {

            }
        )
    }

    fun addLike(replyId: Int) {
        murengRepository.postLikes(replyId,
            onSuccess = {
                Timber.d("좋아요 성공")
            },
            onFailure = {

            }
        )
    }

    fun sortClick() {
        _clickedSort.value = !_clickedSort.value!!
    }

    fun menuClick() {
        if (!_isPop.value!!) {
            _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
            _clickedSort.value = false
            _isPop.value = true
            getPagingReplyData(0)
        } else {
            _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.newest)
            _clickedSort.value = false
            _isPop.value = false
            getPagingReplyData(0)
        }

    }
    fun backClick() {
        _backButton.value = true
    }

    override fun questionItemClick(questionData: QuestionNetwork) {
        //TODO("Not yet implemented")
    }

    override fun answerItemClick(answerData: DiaryNetwork) {
        Intent(MurengApplication.appContext, DiaryDetailActivity::class.java).apply {
            this.putExtra(IntentKey.DIARY, answerData.asDomain())
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
