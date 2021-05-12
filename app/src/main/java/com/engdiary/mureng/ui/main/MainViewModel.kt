package com.engdiary.mureng.ui.main

import android.content.Intent
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import com.engdiary.mureng.ui.home.HomeFragment
import com.engdiary.mureng.ui.my.MyPageFragment
import com.engdiary.mureng.ui.social.SocialFragment
import com.engdiary.mureng.ui.writing.WritingFragment
import com.engdiary.mureng.view.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    private var _selectHome = MutableLiveData<Boolean>()
    var selectHome: LiveData<Boolean> = _selectHome

    private var _selectMyPage = MutableLiveData<Boolean>()
    var selectMyPage: LiveData<Boolean> = _selectMyPage

    private var _selectSocial = MutableLiveData<Boolean>()
    var selectSocial: LiveData<Boolean> = _selectSocial

    private var _selectWriting = MutableLiveData<Boolean>()
    var selectWriting: LiveData<Boolean> = _selectWriting


    /** 생성자 */
    init {
        _selectHome.value = true
        _selectWriting.value = false
        _selectSocial.value = false
        _selectMyPage.value = false
    }

     fun homeClick() {
         _selectHome.value = true
         _selectMyPage.value = false
         _selectSocial.value = false
         _selectWriting.value = false
     }


    fun MypageClick() {
        _selectHome.value = false
        _selectMyPage.value = true
        _selectSocial.value = false
        _selectWriting.value = false
    }


    fun WritingClick() {
        _selectHome.value = false
        _selectMyPage.value = false
        _selectSocial.value = false
        _selectWriting.value = true
    }



    fun SocialClick() {
        _selectHome.value = false
        _selectMyPage.value = false
        _selectSocial.value = true
        _selectWriting.value = false
    }



    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
