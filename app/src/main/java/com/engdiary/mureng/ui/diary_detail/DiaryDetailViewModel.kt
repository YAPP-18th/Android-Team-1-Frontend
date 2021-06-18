package com.engdiary.mureng.ui.diary_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.SingleLiveEvent
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(private val murengRepository: MurengRepository) :
    BaseViewModel(murengRepository) {

    private val _diary = MutableLiveData<Diary>()
    val diary: LiveData<Diary>
        get() = _diary

    val diaryImage = Transformations.map(_diary) { it.image }
    val question = Transformations.map(_diary) { it.question }
    val content = Transformations.map(_diary) { it.content }
    val author = Transformations.map(_diary) { it.author }

    private val _navigateToBefore = SingleLiveEvent<Unit>()
    val navigateToBefore: LiveData<Unit>
        get() = _navigateToBefore


    fun deleteDiary() {
        viewModelScope.launch {
            _diary.value?.id?.let { murengRepository.deleteDiary(it) }
                ?.takeIf { result -> result }
                ?.run { _navigateToBefore.call() }
        }
    }

    fun setDiary(diary: Diary) {
        _diary.value = diary
    }

    fun getQuestionId(): Int? = question.value?.questionId
}
