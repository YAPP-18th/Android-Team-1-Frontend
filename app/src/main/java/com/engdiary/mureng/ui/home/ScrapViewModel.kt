package com.engdiary.mureng.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

abstract class ScrapViewModel constructor(
        private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    protected var _todayExpression = MutableLiveData<List<TodayExpression>>(listOf())
    open var todayExpression: LiveData<List<TodayExpression>> = _todayExpression

    fun addExpressionResult(questionData: TodayExpression) {
        val results: MutableList<TodayExpression> = _todayExpression.value?.toMutableList() ?: mutableListOf()
        results.add(questionData)
        _todayExpression.value = results
    }

    fun addScrap(expression: TodayExpression) {

        toggleTodayExpression(expression)
        if(expression.scrappedByRequester!!) {
            murengRepository.deleteScrap(expression.expId,
                    onSuccess = {

                    },
                    onFailure = {
                    }
            )

        } else {


            murengRepository.postScrap(expression.expId,
                    onSuccess = {

                    },
                    onFailure = {

                    }
            )
        }

    }

    fun deleteScrap (expression: TodayExpression) {
        murengRepository.deleteScrap(expression.expId,
                onSuccess = {
                    viewModelScope.launch {
                        deleteAnswerResult(expression)
                    }
                },
                onFailure = {
                }
        )
    }

    /** [_results] 내 특정 아이템의 좋아요 클릭 여부 내용만 바꿔준다. */
    fun toggleTodayExpression(preTodayExpression: TodayExpression) {
        val results: MutableList<TodayExpression> = _todayExpression.value?.toMutableList() ?: return

        val replaceIndex = results.indexOf(preTodayExpression)

        if (replaceIndex == -1) {
            Timber.i("$preTodayExpression isn't exist")
            return
        } else {
            results.removeAt(replaceIndex)
            results.add(replaceIndex, preTodayExpression.copy(scrappedByRequester = preTodayExpression.clickedScrap))
            _todayExpression.value = results
        }
    }


    /** [_results] 내 특정 아이템을 삭제한다. */
    fun deleteAnswerResult(answerData: TodayExpression) {
        val results: MutableList<TodayExpression> = _todayExpression.value?.toMutableList() ?: return
        val deleteIndex = results.indexOf(answerData)
        if (deleteIndex == -1) {
            Timber.i("$answerData isn't exist")
            return
        } else {
            results.removeAt(deleteIndex)
            _todayExpression.value = results
        }
    }

}
