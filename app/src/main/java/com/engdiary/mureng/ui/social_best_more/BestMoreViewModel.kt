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
    val backButton: LiveData<Boolean> = _backButton

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
        _backButton.value = false
        _isAns.value = false
        _clickedSort.value = false
        _totalCnt.value = 0
        _isPop.value = true
        _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)

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
    fun getAnswerData(page : Int) {
        var sort : String? = null
        sort = if(_isPop.value!!) {
            SortConstant.POP
        } else {
            SortConstant.NEW
        }

        murengRepository.getAnswerList(page = page , size = 10, sort = sort,
            onSuccess = {
                if(page > 0) {
                    for (item in it.data!!) {
                        addAnswerResult(item)
                    }

                } else {
                    _ansResults.value = it.data!!
                    _totalCnt.value = it.totalItemSize!!
                    _totalPage.value = it.totalPage!!
                }
            },
            onFailure = {
                Timber.d("AnswerList 가져오기 통신 실패")
            }
        )
    }

    fun getQuestionData(page : Int) {
        var sort : String? = null
        sort = if(_isPop.value!!) {
            SortConstant.POP
        } else {
            SortConstant.NEW
        }
        murengRepository.getQuestionList(page = page, size = 10, sort = sort,
            onSuccess = {
                var questionData : MutableList<QuestionNetwork> = it.data!!.toMutableList()
                for (i in 0 until questionData.size) {
                    questionData[i].lineVisible =  true
                }

                if(page > 0) {
                    for (item in questionData) {
                        addQuestionResult(item)
                    }

                } else {
                    _quesResults.value = questionData
                    _totalCnt.value = it.totalItemSize!!
                    _totalPage.value = it.totalPage!!
                }
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
        Timber.e("menuClick - ${_isPop.value}")
        if (!_isPop.value!!) {
            _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.popular)
            _isPop.value = true
            _clickedSort.value = false
            Timber.d("인기순 정렬")

            if(_isAns.value!!) {
               getAnswerData(0)
            } else {
                getQuestionData(0)
            }

        } else {
            _selectedSort.value = MurengApplication.getGlobalAppApplication().getString(R.string.newest)
            _isPop.value = false
            Timber.d("최신순 정렬")
            _clickedSort.value = false

            if(_isAns.value!!) {
                getAnswerData(0)
            } else {
                getQuestionData(0)
            }
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
