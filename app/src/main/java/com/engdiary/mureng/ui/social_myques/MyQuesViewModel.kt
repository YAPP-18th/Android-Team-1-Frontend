package com.engdiary.mureng.ui.social_myques

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.social_best.BestPopularViewModel
import com.engdiary.mureng.ui.social_detail.SocialDetailActivity
import com.engdiary.mureng.ui.social_qcreate.SocialQcreateActivity
import dagger.hilt.android.lifecycle.HiltViewModel
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
        getMyQuesList()
    }

    fun getMyQuesList() {
        murengRepository.getMyQuestionList(
            onSuccess = {
                _quesResults.value = it
                _quesCnt.value = it.size
            },
            onFailure = {
                Timber.e("나의 질문 리스트 가져오기 통신 실패")
            }
        )
    }

    override fun questionItemClick(questionData: QuestionNetwork) {
        Intent(MurengApplication.appContext, SocialDetailActivity::class.java).apply {
            this.putExtra("quesitonData", questionData)
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

    }

    override fun answerItemClick(answerData: DiaryNetwork) {
        //TODO 안쓰임!!
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
