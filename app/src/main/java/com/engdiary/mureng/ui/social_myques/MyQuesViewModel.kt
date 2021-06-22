package com.engdiary.mureng.ui.social_myques

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.social_best.BestPopularViewModel
import com.engdiary.mureng.ui.social_detail.SocialDetailActivity
import com.engdiary.mureng.ui.social_qcreate.SocialQcreateActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyQuesViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BestPopularViewModel(murengRepository) {

    private var _quesCnt = MutableLiveData<Int>()
    var quesCnt: LiveData<Int> = _quesCnt

    /** 생성자 */
    init {
        _quesCnt.value = 0
        myQuestionListSetting()
    }

    private fun myQuestionListSetting() {
        viewModelScope.launch {
            murengRepository.getMyQuestionList()
                .let {
                    if(it != null) {
                        _quesResults.postValue(it)
                        _quesCnt.value = it.size
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
        //TODO("Not yet implemented")
    }

    override fun answerItemHeartClick(answerData: Diary) {
        //TODO("Not yet implemented")
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }

    override fun onResume() {
        super.onResume()
        myQuestionListSetting()
    }
}
