package com.engdiary.mureng.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(private val murengRepository: MurengRepository) :
    BaseViewModel(murengRepository) {

    private val _records = MutableLiveData<List<Diary>>()
    val records: LiveData<List<Diary>>
        get() = _records

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = murengRepository.getMyInfo()
            user?.let { murengRepository.getUserDiaries(it.memberId) }
                ?.let { _records.postValue(it) }
        }
    }
}
