package com.engdiary.mureng.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.data.response.TodayExpression
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

abstract class ScrapViewModel constructor(
        murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    protected val _todayExpression = MutableLiveData<List<TodayExpression>>(listOf())
    open var todayExpression: LiveData<List<TodayExpression>> = _todayExpression

    /** 생성자 */
    init {
//        getQuestionData()
    }


}
