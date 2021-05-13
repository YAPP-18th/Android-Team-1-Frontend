package com.engdiary.mureng.ui.social_best

import androidx.lifecycle.ViewModel
import com.engdiary.mureng.data.BestAnswerData
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BestTabViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : PopularViewModel(murengRepository) {

    /** 생성자 */
    init {

    }

    override fun questionItemClick(questionData: QuestionData) {
        TODO("Not yet implemented")
    }

    override fun answerItemClick(answerData: BestAnswerData) {
        TODO("Not yet implemented")
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
