package com.engdiary.mureng.ui.my

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.BestMoreConstant
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.constant.SortConstant
import com.engdiary.mureng.data.Record
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import com.engdiary.mureng.ui.social_best_more.BestMoreActivity
import com.engdiary.mureng.ui.social_detail.SocialDetailActivity
import com.engdiary.mureng.ui.social_detail.SocialDetailViewModel
import com.engdiary.mureng.ui.social_qcreate.SocialQcreateActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AwardViewModel @Inject constructor(
        private val murengRepository: MurengRepository,
        private val authManager: AuthManager
) : BaseViewModel(murengRepository) {

    private val _murengTrey: MutableLiveData<MutableList<Record>> = MutableLiveData(mutableListOf())
    val murengTrey: LiveData<MutableList<Record>> get() = _murengTrey

    private val _murengCnt: MutableLiveData<Int> = MutableLiveData()
    val murengCnt: LiveData<Int> get() = _murengCnt

    private val _badgeThree: MutableLiveData<Boolean> = MutableLiveData()
    val badgeThree: LiveData<Boolean> get() = _badgeThree

    private val _badgeStudy: MutableLiveData<Boolean> = MutableLiveData()
    val badgeStudy: LiveData<Boolean> get() = _badgeStudy

    private val _badgeCeleb: MutableLiveData<Boolean> = MutableLiveData()
    val badgeCeleb: LiveData<Boolean> get() = _badgeCeleb

    /** 생성자 */
    init {
        _murengCnt.value = 0
        _badgeStudy.value = false
        _badgeCeleb.value = false
        _badgeThree.value = false
        _murengTrey.value = mutableListOf<Record>()

        getUserAward()
    }

    private fun getUserAward() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = murengRepository.getMyInfo()
            user?.let { murengRepository.getUserAchievement(it.memberId) }
                .let {
                    for (item in it!!.badges) {
                        when (item.name) {
                            "머렝 3일" -> _badgeThree.value = true
                            "학구적 머렝" -> _badgeStudy.value = true
                            "셀럽 머렝" -> _badgeCeleb.value = true
                        }
                    }

                    if(it.member.murengCount != 0) {
                        _murengCnt.value = it.member.murengCount
                        for (i in 0 .. it.member.murengCount) {
                            when(i % 8) {
                                0 -> _murengTrey.value!!.add(Record(R.drawable.mureng_8))
                                1 -> _murengTrey.value!!.add(Record(R.drawable.mureng_1))
                                2 -> _murengTrey.value!!.add(Record(R.drawable.mureng_2))
                                3 -> _murengTrey.value!!.add(Record(R.drawable.mureng_3))
                                4 -> _murengTrey.value!!.add(Record(R.drawable.mureng_4))
                                5 -> _murengTrey.value!!.add(Record(R.drawable.mureng_5))
                                6 -> _murengTrey.value!!.add(Record(R.drawable.mureng_6))
                                7 -> _murengTrey.value!!.add(Record(R.drawable.mureng_7))
                            }
                        }
                    }

                }
        }
    }



    override fun onResume() {
        super.onResume()
    }

    override fun onCleared() {
        super.onCleared()
    }
}
