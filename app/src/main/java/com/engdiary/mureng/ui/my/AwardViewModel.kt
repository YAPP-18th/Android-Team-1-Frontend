package com.engdiary.mureng.ui.my

import androidx.lifecycle.*
import com.engdiary.mureng.data.domain.Award
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AwardViewModel @Inject constructor(
        private val murengRepository: MurengRepository,
        private val authManager: AuthManager
) : BaseViewModel(murengRepository) {

    private val _badgeThree: MutableLiveData<Boolean> = MutableLiveData()
    val badgeThree: LiveData<Boolean> get() = _badgeThree

    private val _badgeStudy: MutableLiveData<Boolean> = MutableLiveData()
    val badgeStudy: LiveData<Boolean> get() = _badgeStudy

    private val _badgeCeleb: MutableLiveData<Boolean> = MutableLiveData()
    val badgeCeleb: LiveData<Boolean> get() = _badgeCeleb

    private val _userAward: MutableLiveData<Award> = MutableLiveData()
    val userAward: LiveData<Award> get() = _userAward

    private val _murengCnt: MutableLiveData<Int> = MutableLiveData()
    val murengCnt: LiveData<Int> get() = _murengCnt

    /** 생성자 */
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = murengRepository.getMyInfo()
            user?.let { murengRepository.getUserAchievement(it.memberId) }
                .let {
                    _userAward.postValue(it!!)
                    _murengCnt.postValue(it!!.member.murengCount)
                    for (item in it!!.badges) {
                        when (item.name) {
                            "머렝 3일" -> _badgeThree.postValue(true)
                            "학구적 머렝" -> _badgeStudy.postValue(true)
                            "셀럽 머렝" -> _badgeCeleb.postValue(true)
                        }
                    }
                }

        }
        _badgeStudy.value = false
        _badgeCeleb.value = false
        _badgeThree.value = false


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCleared() {
        super.onCleared()
    }
}
