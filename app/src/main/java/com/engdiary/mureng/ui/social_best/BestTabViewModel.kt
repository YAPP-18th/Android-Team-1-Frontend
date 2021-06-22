package com.engdiary.mureng.ui.social_best

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.constant.BestMoreConstant
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.constant.SortConstant
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import com.engdiary.mureng.ui.social_best_more.BestMoreActivity
import com.engdiary.mureng.ui.social_detail.SocialDetailActivity
import com.engdiary.mureng.ui.social_detail.SocialDetailViewModel
import com.engdiary.mureng.ui.social_qcreate.SocialQcreateActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BestTabViewModel @Inject constructor(
        private val murengRepository: MurengRepository,
        private val authManager: AuthManager
) : BestPopularViewModel(murengRepository) {

    /** 생성자 */
    init {
        viewModelScope.launch {
            getAnswerData()
            getQuestionData()
        }
    }

    private suspend fun getAnswerData() {
       murengRepository.getAnswerList(page = 0 , size = 5, sort = SortConstant.POP)
               .let {
                   _ansResults.postValue(it?.data?.map { item -> item.asDomain() })
               }
    }

    private suspend fun getQuestionData() {
        murengRepository.getQuestionList(page = 0, size = 3, sort = SortConstant.POP)
                .let {
                    var questionData = it?.data?.map{ item -> item.asDomain() }
                    if(questionData != null) {
                        _quesResults.postValue(questionData)
                    }
                }
    }

    fun quesMoreClick() {
        Intent(MurengApplication.appContext, BestMoreActivity::class.java).apply {
            this.putExtra("mode", BestMoreConstant.QUES)
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

    }

    fun ansMoreClick() {
        Intent(MurengApplication.appContext, BestMoreActivity::class.java).apply {
            this.putExtra("mode", BestMoreConstant.ANS)
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
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
       // 안쓰임
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }

    override fun onResume() {
        super.onResume()
        viewModelScope.launch {
            getAnswerData()
            getQuestionData()
        }
    }
}
