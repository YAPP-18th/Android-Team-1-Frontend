package com.engdiary.mureng.ui.social_best_more

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.BestMoreConstant
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.constant.SortConstant
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import com.engdiary.mureng.ui.social_best.BestPopularViewModel
import com.engdiary.mureng.ui.social_detail.SocialDetailActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BestMoreViewModel @Inject constructor(
    private val murengRepository: MurengRepository,
) : BestPopularViewModel(murengRepository) {

    private var _backButton = MutableLiveData<Boolean>()
    var backButton: LiveData<Boolean> = _backButton

    private val _barTitle = MutableLiveData<String>()
    val barTitle : LiveData<String> = _barTitle

    private val _barContent = MutableLiveData<String>()
    val barContent : LiveData<String> = _barContent

    private var _totalCnt = MutableLiveData<Int>()
    var totalCnt : LiveData<Int> = _totalCnt

    private val _barImage = MutableLiveData<Int>()
    val barImage : LiveData<Int> = _barImage

    private var _isAns = MutableLiveData<Boolean>()
    var isAns: LiveData<Boolean> = _isAns

    private var _clickedSort = MutableLiveData<Boolean>()
    var clickedSort : LiveData<Boolean> = _clickedSort

    private var _selectedSort = MutableLiveData<String>()
    var selectedSort : LiveData<String> = _selectedSort

    private var _isPop = MutableLiveData<Boolean>()
    var isPop : LiveData<Boolean> = _isPop

    private var page: Int = 0


    init {
        _backButton.value = false
        _isAns.value = false
        _clickedSort.value = false
        _totalCnt.value = 0

    }

    fun setMode(mode : String){
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

    fun paging(mode : String) {
        if (mode == BestMoreConstant.ANS) {
            this.page = page
            _isAns.value = true
            _barTitle.value = "ANSWERS"
            _barContent.value = "다른 사람들은\n어떤 답을 썼을까요?"
            _barImage.value = R.drawable.icons_symbol_cheek_blue
            getAnswerData(page)

        } else {
            this.page = page
            _isAns.value = false
            _barTitle.value = "QUESTIONS"
            _barContent.value = "어떤 질문들이\n나를 기다릴까요?"
            _barImage.value = R.drawable.icons_symbol_cheek_pink
            getQuestionData(page)
        }
    }

    private fun getAnswerData(page : Int) {
        murengRepository.getAnswerList(page = page , size = 10, sort = SortConstant.POP,
            onSuccess = {
                _ansResults.value = it.data!!
                _totalCnt.value = it.totalItemSize!!
                _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
                _isPop.value = true
                _clickedSort.value = false
                _ansTotal.value = it.totalItemSize!!
            },
            onFailure = {
                Timber.d("AnswerList 가져오기 통신 실패")
            }
        )
    }

    private fun getQuestionData(page : Int) {
        murengRepository.getQuestionList(page = page, size = 10, sort = SortConstant.POP,
            onSuccess = {
                var questionData : MutableList<QuestionNetwork> = it.data!!.toMutableList()
                for (i in 0 until questionData.size) {
                    questionData[i].lineVisible =  true
                }
                _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
                _isPop.value = true
                _quesResults.value = questionData
                _totalCnt.value = it.totalItemSize!!
                _ansTotal.value = it.totalItemSize!!
            },
            onFailure = {
                Timber.d("QuestionList 가져오기 통신 실패")
            }
        )
    }

    fun sortClick() {
        _clickedSort.value = !_clickedSort.value!!
    }

    fun menuClick() {
        if (!_isPop.value!!) {
            if(_isAns.value!!) {
               getAnswerData(page)
            } else {
                getQuestionData(page)
            }
            _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
            _isPop.value = true
            _clickedSort.value = false
        } else {
            if(_isAns.value!!) {
                getAnswerData(page)
            } else {
                getQuestionData(page)
            }
            _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.newest)
            _isPop.value = false
            _clickedSort.value = false
        }

    }

    fun backClick() {
        _backButton.value = true
    }

    override fun questionItemClick(questionData: QuestionNetwork) {
        Intent(MurengApplication.appContext, SocialDetailActivity::class.java).apply {
            this.putExtra("quesitonData", questionData)
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
    override fun answerItemClick(answerData: DiaryNetwork) {
        Intent(MurengApplication.appContext, DiaryDetailActivity::class.java).apply {
            this.putExtra(IntentKey.DIARY, answerData.asDomain())
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
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



    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }

    override fun onResume() {
        super.onResume()
    }
}
