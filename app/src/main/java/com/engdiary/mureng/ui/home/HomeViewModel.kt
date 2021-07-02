package com.engdiary.mureng.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.domain.CheckReplied
import com.engdiary.mureng.data.domain.Diary
import com.engdiary.mureng.data.domain.Question
import com.engdiary.mureng.data.domain.QuestionRefresh
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.math.floor

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : ScrapViewModel(murengRepository) {

    private val _checkReplied = MutableLiveData<CheckReplied>()
    val checkReplied: LiveData<CheckReplied> = _checkReplied

    private var _questionTitle = MutableLiveData<String>()
    var questionTitle : LiveData<String> = _questionTitle

    private var _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    private var _timerCount = MutableLiveData<Int>()
    val timerCount : LiveData<Int> = _timerCount

    private lateinit var timerJob : Job


    /** 생성자 */
    init {
        _userName.value = ""
        _questionTitle.value = ""
        _timerCount.value = 0
        viewModelScope.launch(Dispatchers.IO) {
            murengRepository.getMyInfo()
                    ?.let { _userName.postValue(it.nickname) }

            murengRepository.getCheckReplied()?.let {
                _checkReplied.postValue(it)
                if(it.replied) {
                    timerStart()

                } else {
                    timerEnd()
                }
            }

            murengRepository.getTodayExpression()
                    .let {
                        if(it != null) {
                            _todayExpression.postValue(it)
                        }
                    }
            _questionTitle.postValue(murengRepository.getTodayQuestion()?.content)

        }
    }

    fun getTime() {
        val date = Date()
        Timber.e("date${date}")
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val c_hour = calendar[Calendar.HOUR_OF_DAY]
        val c_min = calendar[Calendar.MINUTE]
        val c_sec = calendar[Calendar.SECOND]


        val baseCal: Calendar = GregorianCalendar(year, month, day, c_hour, c_min, c_sec)
        val targetCal: Calendar = GregorianCalendar(year, month, day + 1, 0, 0, 0)

        val diffSec = (targetCal.timeInMillis - baseCal.timeInMillis) / 1000
        val hourTime = floor((diffSec / 3600).toDouble()).toInt()

        _timerCount.value = hourTime

    }

    fun timerStart() {
        if(::timerJob.isInitialized) timerJob.cancel()

        Timber.e("ddd?")
        var hourTime = 12
        timerJob = viewModelScope.launch {
            while (hourTime > 0){
                val date = Date()
                Timber.d("date${date}")
                val calendar: Calendar = GregorianCalendar()
                calendar.time = date
                val year = calendar[Calendar.YEAR]
                val month = calendar[Calendar.MONTH]
                val day = calendar[Calendar.DAY_OF_MONTH]
                val hour = calendar[Calendar.HOUR_OF_DAY]
                val min = calendar[Calendar.MINUTE]
                val sec = calendar[Calendar.SECOND]


                val baseCal: Calendar = GregorianCalendar(year, month, day, hour, min, sec)
                val targetCal: Calendar = GregorianCalendar(year, month, day + 1, 0, 0, 0)
                val diffSec = (targetCal.timeInMillis - baseCal.timeInMillis) / 1000
                Timber.d("diff - ${diffSec}")
                hourTime = floor((diffSec / 3600).toDouble()).toInt()
                _timerCount.value = hourTime
                delay(1000L)
            }
        }

    }

    fun timerEnd() {
        if(::timerJob.isInitialized) timerJob.cancel()
    }

    fun getQuestionData() {
        viewModelScope.launch {
            try {
                _questionTitle.postValue(murengRepository.getTodayQuestionRefresh()?.content)

            } catch (networkError: IOException) {
            }
        }
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
        timerEnd()
    }
}
