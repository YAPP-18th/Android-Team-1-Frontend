package com.engdiary.mureng.ui.write_diary

import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.*
import com.engdiary.mureng.data.ItemWriteDiaryImage.DiaryImage.Companion.FROM_SERVER_ID
import com.engdiary.mureng.di.MEDIA_BASE_URL
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class WriteDiaryImageViewModel @ViewModelInject constructor(
    private val murengRepository: MurengRepository,
) : BaseViewModel(murengRepository) {

    private val _selectedImage = MutableLiveData<ItemWriteDiaryImage>()
    val selectedImage: LiveData<ItemWriteDiaryImage>
        get() = _selectedImage

    private val _selectedGalleryImage = MutableLiveData<Uri>()
    val selectedGalleryImage: LiveData<Uri>
        get() = _selectedGalleryImage

    private val _diaryImages = MutableLiveData<List<ItemWriteDiaryImage.DiaryImage>>()
    val diaryImages: LiveData<List<ItemWriteDiaryImage.DiaryImage>>
        get() = _diaryImages

    private val diaryContent = MutableLiveData<DiaryContent>()

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var editingDiaryId: Int? = null

    private val _navigateToNewDiaryDetail = SingleLiveEvent<Diary>()
    val navigateToNewDiaryDetail: LiveData<Diary>
        get() = _navigateToNewDiaryDetail

    private val _navigateToEditedDiaryDetail = SingleLiveEvent<Diary>()
    val navigateToEditedDiaryDetail: LiveData<Diary>
        get() = _navigateToEditedDiaryDetail

    init {
        viewModelScope.launch {
            _diaryImages.value = murengRepository.getDefaultDiaryImages()
        }
    }

    fun selectGalleryImage(uri: Uri?) {
        uri?.let { _selectedGalleryImage.value = it } ?: return
        selectImage(ItemWriteDiaryImage.Gallery())
    }

    fun selectImage(itemWriteDiaryImage: ItemWriteDiaryImage) {
        _selectedImage.value
            ?.takeIf { it is ItemWriteDiaryImage.DiaryImage }
            ?.run { (this as ItemWriteDiaryImage.DiaryImage).setIsSelected(false) }

        if (itemWriteDiaryImage is ItemWriteDiaryImage.DiaryImage) {
            itemWriteDiaryImage.setIsSelected(true)
        }
        _selectedImage.value = itemWriteDiaryImage
    }

    fun requestWriteDiary() {
        viewModelScope.launch { postDiary() }
    }

    private suspend fun postDiary() {
        val imagePath = when (_selectedImage.value) {
            is ItemWriteDiaryImage.DiaryImage -> (_selectedImage.value as ItemWriteDiaryImage.DiaryImage).imagePath
            is ItemWriteDiaryImage.Gallery -> murengRepository.postDiaryImage(_selectedGalleryImage.value)
            null -> return
        }
        val isEditing = checkIsEditing(editingDiaryId)
        if (isEditing) {
            murengRepository.putDiary(
                editingDiaryId!!,
                question?.value?.questionId!!,
                diaryContent.value!!,
                imagePath!!
            )?.let { diary -> _navigateToEditedDiaryDetail.value = diary }
            return
        }

        imagePath?.let {
            murengRepository.postDiary(question.value?.questionId!!, diaryContent.value!!, it)
                ?.let { diary -> _navigateToNewDiaryDetail.value = diary }
                .run { _navigateToNewDiaryDetail.call() }
        }
    }

    private fun checkIsEditing(diaryId: Int?) = diaryId != null

    fun setDiaryContent(diaryContent: DiaryContent) {
        this.diaryContent.value = diaryContent
    }

    fun setDiaryImages(diary: Diary) {
        _selectedImage.value = ItemWriteDiaryImage.DiaryImage(
            FROM_SERVER_ID,
            MEDIA_BASE_URL + diary.image,
            diary.image,
            ObservableBoolean(true)
        )
        this.editingDiaryId = diary.id
    }

    fun setQuestion(question: Question) {
        this._question.value = question
    }
}
