package com.engdiary.mureng.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

abstract class ScrapViewModel constructor(
        murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    protected var _todayExpression = MutableLiveData<List<TodayExpression>>(listOf())
    open var todayExpression: LiveData<List<TodayExpression>> = _todayExpression

    fun addQuestionResult(questionData: QuestionNetwork) {
//        val results: MutableList<QuestionNetwork> = _quesResults.value?.toMutableList() ?: mutableListOf()
//        results.add(questionData)
//        _quesResults.value = results
    }

    fun addQuestionResult(questionData: TodayExpression) {
        val results: MutableList<TodayExpression> = _todayExpression.value?.toMutableList() ?: mutableListOf()
        results.add(questionData)
//        val test: MutableList<TodayExpression> = questionData as MutableList<TodayExpression>
        _todayExpression.value = results
    }

//    murengRepository.getQuestionList(page = page, size = 10, sort = sort,
//    onSuccess = {
//        var questionData : MutableList<QuestionNetwork> = it.data!!.toMutableList()
//        for (i in 0 until questionData.size) {
//            questionData[i].lineVisible =  true
//        }
//
//        if(page > 0) {
//            for (item in questionData) {
//                addQuestionResult(item)
//            }
//
//        } else {
//            _quesResults.value = questionData
//            _totalCnt.value = it.totalItemSize!!
//            _totalPage.value = it.totalPage!!
//        }
//    },
//    onFailure = {
//        Timber.d("QuestionList 가져오기 통신 실패")
//    }
    /** 생성자 */
    init {
//        getQuestionData()
    }


}
