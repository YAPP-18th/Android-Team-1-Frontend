package com.engdiary.mureng.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.domain.PushAlertSetting
import com.engdiary.mureng.data.domain.SingleLiveEvent
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val murengRepository: MurengRepository) :
    BaseViewModel(murengRepository) {

    private val _navigateToPushAlert = SingleLiveEvent<PushAlertSetting>()
    val navigateToPushAlert: LiveData<PushAlertSetting>
        get() = _navigateToPushAlert

    private val _navigateToLogin = SingleLiveEvent<Unit>()
    val navigateToLogin: LiveData<Unit>
        get() = _navigateToLogin

    fun navigateToPushAlert() {
        viewModelScope.launch(Dispatchers.IO) {
            murengRepository.getMyInfo()?.pushAlertSetting
                ?.let { _navigateToPushAlert.postValue(it) }
        }
    }

    fun withdrawMureng() {
        viewModelScope.launch(Dispatchers.IO) {
            takeIf { murengRepository.withdrawMureng() }
                .run { _navigateToLogin.postValue(Unit) }
        }
    }

    fun expireAccessToken() {
        murengRepository.expireAccessToken()
    }
}
