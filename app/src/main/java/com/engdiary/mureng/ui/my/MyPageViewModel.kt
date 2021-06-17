package com.engdiary.mureng.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import com.engdiary.mureng.ui.social.SocialViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    private val _tabItems: MutableLiveData<List<String>> = MutableLiveData()
    private val _position: MutableLiveData<Int> = MutableLiveData()
    val tabItems: LiveData<List<String>> get() = _tabItems
    val position: LiveData<Int> get() = _position


    /** 생성자 */
    init {
        _tabItems.postValue(MyPageViewModel.TAB_ITEMS)
    }

    fun selectPosition(position: Int) {
        _position.postValue(position)
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        private val TAB_ITEMS = listOf("기록", "성과", "스크랩")
    }
}