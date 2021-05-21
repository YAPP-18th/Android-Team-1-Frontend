package com.engdiary.mureng.ui.social_best

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engdiary.mureng.constant.SortConstant
import com.engdiary.mureng.data.BestAnswerData
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import com.engdiary.mureng.ui.social_best_more.BestMoreActivity
import com.engdiary.mureng.ui.social_qcreate.SocialQcreateActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BestTabViewModel @Inject constructor(
    private val murengRepository: MurengRepository,
    private val authManager: AuthManager
) : PopularViewModel(murengRepository) {

    /** 생성자 */
    init {

        getQuestionData()
        getAnswerData()

    }

    fun getAnswerData() {

    }

    fun getQuestionData() {

        murengRepository.getQuestionList(page = 0, size = 3, sort = SortConstant.POP,
            onSuccess = {
                        _quesResults.value = it
            },
            onFailure = {
                Timber.d("QuestionList 가져오기 통신 실패")
            }
        )

    }

    fun quesMoreClick() {
        Intent(MurengApplication.appContext, BestMoreActivity::class.java).apply {
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

    }

    fun ansMoreClick() {
        Intent(MurengApplication.appContext, BestMoreActivity::class.java).apply {
        }.run {
            MurengApplication.getGlobalApplicationContext().startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }


    override fun questionItemClick(questionData: QuestionNetwork) {
        // TODO("Not yet implemented")
    }


    override fun answerItemClick(answerData: BestAnswerData) {
        // TODO("Not yet implemented")
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
