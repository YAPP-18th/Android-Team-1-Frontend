package com.engdiary.mureng.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.Author
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    private val _position: MutableLiveData<Int> = MutableLiveData()
    val position: LiveData<Int> get() = _position

    private val _user: MutableLiveData<Author> = MutableLiveData()
    val user: LiveData<Author> get() = _user

    init {
        initUserInfo()
    }

    private fun initUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            murengRepository.getMyInfo()
                ?.let { _user.postValue(it) }
        }
    }
}
