package com.engdiary.mureng.ui.write_diary

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.DiaryContent
import com.engdiary.mureng.data.ItemWriteDiaryImage
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

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

    init {
        viewModelScope.launch {
            _diaryImages.value = murengRepository.getDiaryImages()
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
            is ItemWriteDiaryImage.Gallery -> murengRepository.postDiaryImage(_selectedGalleryImage.value)?.imagePath
            null -> return
        }
        val diaryId = imagePath?.let { murengRepository.postDiary(diaryContent.value!!, it) }
        Timber.d("diaryId: $diaryId")
    }

    fun setDiaryContent(diaryContent: DiaryContent) {
        this.diaryContent.value = diaryContent
    }
}
