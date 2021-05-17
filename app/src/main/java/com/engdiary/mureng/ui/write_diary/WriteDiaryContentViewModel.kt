package com.engdiary.mureng.ui.write_diary

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.DiaryContent
import com.engdiary.mureng.data.SingleLiveEvent
import com.engdiary.mureng.data.TodayQuestion
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class WriteDiaryContentViewModel @ViewModelInject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {
    private val _todayQuestion = MutableLiveData<TodayQuestion>()
    val todayQuestion: LiveData<TodayQuestion>
        get() = _todayQuestion

    val hints = Transformations.map(_todayQuestion) { it.hint }

    val diaryContent = MutableLiveData<String>()

    val isDiaryContentConditionSatisfied = Transformations.map(diaryContent) {
        it != null && it.isNotBlank() && DiaryContent.checkMinLengthCondition(it)
    }

    private val _isHintOpen = MutableLiveData(false)
    val isHintOpen: LiveData<Boolean>
        get() = _isHintOpen

    private val _showKoreanExistDialog = SingleLiveEvent<Unit>()
    val showKoreanExistDialog: LiveData<Unit>
        get() = _showKoreanExistDialog

    private val _navigateToWritingDiaryImage = SingleLiveEvent<DiaryContent>()
    val navigateToWritingDiaryImage: LiveData<DiaryContent>
        get() = _navigateToWritingDiaryImage

    init {
        viewModelScope.launch {
            try {
                _todayQuestion.value = murengRepository.getTodayQuestion()
                Timber.d("Get Today Question Success: (${_todayQuestion.value})")
            } catch (networkError: IOException) {
                Timber.d("Get Today Question Error: (cause: ${networkError.cause}) (message: ${networkError.message})")
            }
        }
    }

    fun checkKoreanIsExist() {
        val isSatisfied = diaryContent.value?.takeIf { it.isNotBlank() }
            ?.let { DiaryContent.checkLanguageCondition(it) } ?: return
        if (!isSatisfied) {
            _showKoreanExistDialog.call()
            return
        }

        diaryContent.value?.let {
            _navigateToWritingDiaryImage.value = DiaryContent.of(it)
        }?.run { _navigateToWritingDiaryImage.call() }
    }

    fun toggleHint() {
        _isHintOpen.value = _isHintOpen.value?.not()
    }
}
