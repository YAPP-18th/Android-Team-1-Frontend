package com.engdiary.mureng.ui.social_myques

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyQuesViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {


    private var _isEmpty = MutableLiveData<Boolean>()
    var isEmpty: LiveData<Boolean> = _isEmpty


    private var _quesCnt = MutableLiveData<Int>()
    var quesCnt: LiveData<Int> = _quesCnt

    private var _quesList: MutableLiveData<QuestionData> = MutableLiveData()
    var quesList: LiveData<QuestionData> = _quesList


    /** 생성자 */
    init {
        _isEmpty.value = false
        _quesCnt.value = 0

    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
