package com.engdiary.mureng.ui.diary_detail

import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(private val murengRepository: MurengRepository) :
    BaseViewModel(murengRepository) {

}
