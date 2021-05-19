package com.engdiary.mureng.ui.push_alert

import androidx.hilt.lifecycle.ViewModelInject
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel

class PushAlertViewModel @ViewModelInject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

}
