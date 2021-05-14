package com.engdiary.mureng.ui.write_diary

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.engdiary.mureng.network.MurengRepository

class WritingDiaryImageViewModel @ViewModelInject constructor(
    private val murengRepository: MurengRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}