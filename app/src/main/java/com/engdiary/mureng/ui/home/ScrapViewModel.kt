package com.engdiary.mureng.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
        //        murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    protected var _todayExpression = MutableLiveData<List<TodayExpression>>(listOf())
    open var todayExpression: LiveData<List<TodayExpression>> = _todayExpression

    fun addExpressionResult(questionData: TodayExpression) {
        val results: MutableList<TodayExpression> = _todayExpression.value?.toMutableList() ?: mutableListOf()
        results.add(questionData)
        _todayExpression.value = results
    }

    fun addScrap(expression: TodayExpression) {

        if(expression.scrappedByRequester) {

            murengRepository.deleteScrap(expression.expId,
                    onSuccess = {
                        viewModelScope.launch {
                            try {

                                _todayExpression.value = murengRepository.getTodayExpression()
                            } catch (networkError: IOException) {
                            }
                        }
                    },
                    onFailure = {
                    }
            )
        } else {
            murengRepository.postScrap(expression.expId,
                    onSuccess = {
                        viewModelScope.launch {
                            try {
                                _todayExpression.value = murengRepository.getTodayExpression()
                            } catch (networkError: IOException) {
                            }
                        }
                    },
                    onFailure = {

                    }
            )
        }

    }

}
