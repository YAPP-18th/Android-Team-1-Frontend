package com.engdiary.mureng.ui.write_diary

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.domain.*
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class WriteDiaryContentViewModel @ViewModelInject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    val diary = MutableLiveData<Diary>()
    val diaryContent = MutableLiveData<String>()

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _author = MutableLiveData<Author>()
    val author: LiveData<Author>
        get() = _author

    val hints = Transformations.map(_question) { it.hints }

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
            _author.value = murengRepository.getMyInfo()
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
        }
    }

    fun toggleHint() {
        _isHintOpen.value = _isHintOpen.value?.not()
    }

    fun setDiary(diary: Diary) {
        this.diary.value = diary
        this.diaryContent.value = diary.content.content
        this._question.value = diary.question
    }

    fun setQuestion(question: Question) {
        this._question.value = question
    }
}
