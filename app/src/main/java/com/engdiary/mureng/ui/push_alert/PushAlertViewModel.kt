package com.engdiary.mureng.ui.push_alert

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.domain.PushAlertSetting
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PushAlertViewModel @ViewModelInject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    val isDailyPushAlertActive = MutableLiveData<Boolean>()

    val isLikePushAlertActive = MutableLiveData<Boolean>()

    fun postDailyPushAlertActive(isActive: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            murengRepository.putDailyPushAlertSetting(isActive)
        }
    }

    fun postLikePushAlertActive(isActive: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            murengRepository.putLikeAlertSetting(isActive)
        }
    }

    fun setPushAlertSetting(pushAlertSetting: PushAlertSetting) {
        isDailyPushAlertActive.value = pushAlertSetting.isDailyPushAlertActive
        isLikePushAlertActive.value = pushAlertSetting.isLikePushAlertActive
    }
}
