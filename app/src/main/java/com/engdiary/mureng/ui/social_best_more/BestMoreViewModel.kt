package com.engdiary.mureng.ui.social_best_more

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.BestMoreConstant
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.constant.SortConstant
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import com.engdiary.mureng.ui.social_best.BestPopularViewModel
import com.engdiary.mureng.ui.social_detail.SocialDetailActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BestMoreViewModel @Inject constructor(
    private val murengRepository: MurengRepository,
) : BestPopularViewModel(murengRepository) {

    private val _barTitle = MutableLiveData<String>()
    val barTitle : LiveData<String> = _barTitle

    private val _barContent = MutableLiveData<String>()
    val barContent : LiveData<String> = _barContent

    private var _totalCnt = MutableLiveData<Int>()
    val totalCnt : LiveData<Int> = _totalCnt

    private val _barImage = MutableLiveData<Int>()
    val barImage : LiveData<Int> = _barImage

    private var _isAns = MutableLiveData<Boolean>()
    val isAns: LiveData<Boolean> = _isAns

    private var _clickedSort = MutableLiveData<Boolean>()
    val clickedSort : LiveData<Boolean> = _clickedSort

    private var _selectedSort = MutableLiveData<String>()
    val selectedSort : LiveData<String> = _selectedSort

    private var _isPop = MutableLiveData<Boolean>()
    val isPop : LiveData<Boolean> = _isPop

    private var _totalPage = MutableLiveData<Int>()
    val totalPage : LiveData<Int> = _totalPage



    init {
        _isAns.value = false
        _clickedSort.value = false
        _totalCnt.value = 0
        _isPop.value = true
        _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
    }

    fun setMode(mode : String){
        viewModelScope.launch {
            if (mode == BestMoreConstant.ANS) {
                _isAns.value = true
                _barTitle.value = "ANSWERS"
                _barContent.value = "다른 사람들은\n어떤 답을 썼을까요?"
                _barImage.value = R.drawable.icons_symbol_cheek_blue
                getAnswerData(0)

            } else {
                _isAns.value = false
                _barTitle.value = "QUESTIONS"
                _barContent.value = "어떤 질문들이\n나를 기다릴까요?"
                _barImage.value = R.drawable.icons_symbol_cheek_pink
                getQuestionData(0)
            }
        }
    }
    fun getAnswerData(page : Int) {
        val sort = if (_isPop.value!!) {
            SortConstant.POP
        } else {
            SortConstant.NEW
        }
        viewModelScope.launch {
            murengRepository.getAnswerList(page = page, size = 10, sort = sort)
                    .let {
                        if (it?.data != null) {
                            val itemList = it.data?.map { item -> item.asDomain() }
                            if (page > 0) {
                                for (item in itemList) {
                                    addAnswerResult(item)
                                }

                            } else {
                                _ansResults.value = itemList
                                _totalCnt.value = it.totalItemSize!!
                                _totalPage.value = it.totalPage!!
                            }
                        }
                    }
        }
    }

    fun getQuestionData(page : Int) {
        val sort = if (_isPop.value!!) {
            SortConstant.POP
        } else {
            SortConstant.NEW
        }

        viewModelScope.launch {
            murengRepository.getQuestionList(page = page, size = 10, sort = sort)
                    .let {
                        if (it?.data != null) {
                            val questionData = it.data?.map{item -> item.asDomain()}

                            if (page > 0) {
                                for (item in questionData) {
                                    addQuestionResult(item)
                                }

                            } else {
                                _quesResults.value = questionData
                                _totalCnt.value = it.totalItemSize!!
                                _totalPage.value = it.totalPage!!
                            }
                        }
                    }
        }
    }

    fun sortClick() {
        _clickedSort.value = !_clickedSort.value!!
    }

    fun menuClick() {
        if (!_isPop.value!!) {
            _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
            _isPop.value = true
            _clickedSort.value = false
            Timber.d("인기순 정렬")
            viewModelScope.launch {
                if(_isAns.value!!) {
                    getAnswerData(0)

                } else {
                    getQuestionData(0)
                }
            }

        } else {
            _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.newest)
            _isPop.value = false
            Timber.d("최신순 정렬")
            _clickedSort.value = false
            viewModelScope.launch {
                if(_isAns.value!!) {
                    getAnswerData(0)

                } else {
                    getQuestionData(0)
                }
            }
        }

    }

    override fun questionItemClick(questionData: Question) {
        Intent(MurengApplication.appContext, SocialDetailActivity::class.java).apply {
            this.putExtra(IntentKey.QUESTION, questionData)
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
    override fun answerItemClick(answerData: Diary) {
        Intent(MurengApplication.appContext, DiaryDetailActivity::class.java).apply {
            this.putExtra(IntentKey.DIARY, answerData)
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun answerItemHeartClick(answerData: Diary) {
        viewModelScope.launch {
            if (answerData.likeYn) deleteLike(answerData.id) else addLike(answerData.id)
        }
    }

    suspend fun deleteLike(replyId : Int) {
        murengRepository.deleteLikes(replyId)
    }

    suspend fun addLike(replyId: Int) {
        murengRepository.postLikes(replyId)
    }


    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }

    override fun onResume() {
        super.onResume()
    }
}
