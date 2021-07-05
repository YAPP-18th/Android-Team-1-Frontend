package com.engdiary.mureng.ui.splash


import androidx.lifecycle.LiveData
import com.engdiary.mureng.data.domain.SingleLiveEvent
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {
    private val _navigateToLogin = SingleLiveEvent<Unit>()
    val navigateToLogin: LiveData<Unit>
        get() = _navigateToLogin

    private val _navigateToMain = SingleLiveEvent<Unit>()
    val navigateToMain: LiveData<Unit>
        get() = _navigateToMain

    fun postRefreshAccessToken() {
        murengRepository.postRefreshAccessToken(
            onSuccess = { _navigateToMain.call() },
            onFailure = { _navigateToLogin.call() },
            onError = { _navigateToLogin.call() }
        )
    }

    /** 생성자 */
    init {

    }

    override fun onCleared() {
        super.onCleared()
    }
}