package com.engdiary.mureng.ui.signup.terms

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupTermsViewModel @Inject constructor(
        private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    private var _selectAllTerms = MutableLiveData<Boolean>()
    var selectAllTerms: LiveData<Boolean> = _selectAllTerms

    private var _selectServiceTerms = MutableLiveData<Boolean>()
    var selectServiceTerms: LiveData<Boolean> = _selectServiceTerms

    private var _selectInfoTerms = MutableLiveData<Boolean>()
    var selectInfoTerms: LiveData<Boolean> = _selectInfoTerms


    init {
        _selectServiceTerms.value = false
        _selectInfoTerms.value = false
        _selectAllTerms.value = false
    }

    fun allTerms() {

        _selectAllTerms.value = _selectAllTerms.value != true

        if(_selectAllTerms.value!!) {
            _selectServiceTerms.value = true
            _selectInfoTerms.value = true
        }

    }

    fun serviceTerms() {

        _selectServiceTerms.value = _selectServiceTerms.value != true

        if(_selectServiceTerms.value!! && _selectInfoTerms.value!!) {
//            _selectAllTerms.value = true
        }

    }

    fun infoTerms() {
        _selectInfoTerms.value = _selectInfoTerms.value != true
    }


    override fun onCleared() {
        super.onCleared()
    }
}