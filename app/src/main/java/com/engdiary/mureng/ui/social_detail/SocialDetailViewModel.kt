package com.engdiary.mureng.ui.social_detail

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.constant.SortConstant
import com.engdiary.mureng.data.domain.Diary
import com.engdiary.mureng.data.domain.Question
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import com.engdiary.mureng.ui.social_best.BestPopularViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialDetailViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BestPopularViewModel(murengRepository) {

    private var _answerCnt = MutableLiveData<Int>()
    val answerCnt: LiveData<Int> = _answerCnt

    private var _questionTitle = MutableLiveData<String>()
    val questionTitle: LiveData<String> = _questionTitle

    private var _questionContent = MutableLiveData<String>()
    val questionContent: LiveData<String> = _questionContent

    private var _questionUser = MutableLiveData<String>()
    val questionUser: LiveData<String> = _questionUser

    private var _clickedSort = MutableLiveData<Boolean>()
    val clickedSort: LiveData<Boolean> = _clickedSort

    private var _selectedSort = MutableLiveData<String>()
    val selectedSort: LiveData<String> = _selectedSort

    private var _questionNetwork = MutableLiveData<Question>()
    val questionNetwork: LiveData<Question> = _questionNetwork

    private var _isPop = MutableLiveData<Boolean>()
    val isPop: LiveData<Boolean> = _isPop

    private var _questionUserImg = MutableLiveData<String>()
    val questionUserImg: LiveData<String> = _questionUserImg

    private var _totalPage = MutableLiveData<Int>()
    val totalPage: LiveData<Int> = _totalPage

    private var _qusetionId = MutableLiveData<Int>()
    val qusetionId: LiveData<Int> = _qusetionId

    /** 생성자 */
    init {
        _answerCnt.value = 0
        _clickedSort.value = false
        _selectedSort.value =
            MurengApplication.getGlobalAppApplication().getString(R.string.popular)
        _isPop.value = true
    }

    fun getQuestionData(questionData: Question) {
        _questionNetwork.value = questionData
        _questionContent.value = questionData.contentKr
        _questionTitle.value = questionData.content
        _qusetionId.value = questionData.questionId
        if (questionData.author != null) {
            _questionUser.value = questionData.author.nickname
            if (questionData.author.image != null) {
                _questionUserImg.value = questionData.author.image!!
            }
        }
        viewModelScope.launch {
            getPagingReplyData(0)
        }
    }

    fun getPagingReplyData(page: Int) {
        var sort: String? = null
        sort = if (_isPop.value!!) {
            SortConstant.POP
        } else {
            SortConstant.NEW
        }
        viewModelScope.launch {
            murengRepository.getReplyAnswerList(
                questionId = _qusetionId.value!!,
                sort = sort,
                size = 10,
                page = page
            )
                .let {
                    if (it?.data != null) {
                        val itemList = it?.data.map { item -> item.asDomain() }
                        if (page > 0) {
                            for (item in itemList) {
                                addAnswerResult(item)
                            }
                        } else {
                            _ansResults.postValue(itemList)
                            _answerCnt.postValue(it.totalItemSize!!)
                            _totalPage.postValue(it.totalPage!!)
                        }
                    }
                }
        }
    }

    override fun answerItemHeartClick(answerData: Diary) {
        viewModelScope.launch {
            if (answerData.likeYn) deleteLike(answerData.id) else addLike(answerData.id)
        }
    }

    suspend fun deleteLike(replyId: Int) {
        murengRepository.deleteLikes(replyId)
    }

    suspend fun addLike(replyId: Int) {
        murengRepository.postLikes(replyId)
    }

    fun sortClick() {
        _clickedSort.value = !_clickedSort.value!!
    }

    fun menuClick() {
        viewModelScope.launch {
            if (!_isPop.value!!) {
                _selectedSort.value =
                    MurengApplication.getGlobalAppApplication().getString(R.string.popular)
                _clickedSort.value = false
                _isPop.value = true
                getPagingReplyData(0)
            } else {
                _selectedSort.value =
                    MurengApplication.getGlobalAppApplication().getString(R.string.newest)
                _clickedSort.value = false
                _isPop.value = false
                getPagingReplyData(0)
            }
        }

    }

    override fun questionItemClick(questionData: Question) {
        //TODO("Not yet implemented")
    }

    override fun answerItemClick(answerData: Diary) {
        Intent(MurengApplication.appContext, DiaryDetailActivity::class.java).apply {
            this.putExtra(IntentKey.DIARY, answerData)
        }.run {
            MurengApplication.getGlobalApplicationContext()
                .startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
