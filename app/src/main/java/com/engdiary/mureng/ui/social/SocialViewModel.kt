package com.engdiary.mureng.ui.social

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.ViewPager
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    private val _tabItems: MutableLiveData<List<String>> = MutableLiveData()
    private val _position: MutableLiveData<Int> = MutableLiveData()
    val tabItems: LiveData<List<String>> get() = _tabItems
    val position: LiveData<Int> get() = _position

    /** 생성자 */
    init {
        _tabItems.postValue(TAB_ITEMS)
    }

    fun selectPosition(position: Int) {
        _position.postValue(position)
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        private val TAB_ITEMS = listOf("BEST","MY QUES.")
    }



}
