package com.engdiary.mureng.ui.social_qcreate

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.request.PostQuestioRequest
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern
import java.util.regex.Pattern.compile
import java.util.regex.Pattern.matches
import javax.inject.Inject

@HiltViewModel
class SocialQcreateViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {

    private var _qCreateEngQues = MutableLiveData<String>()
    var qCreateEngQues : LiveData<String> = _qCreateEngQues

    private var _qCreateKorQues = MutableLiveData<String>()
    var qCreateKorQues : LiveData<String> = _qCreateKorQues

    private var _warningKor = MutableLiveData<Boolean>()
    val warningKor : LiveData<Boolean> = _warningKor

    private var _warningEng = MutableLiveData<Boolean>()
    val warningEng : LiveData<Boolean> = _warningEng

    private var _warningMaxKor = MutableLiveData<Boolean>()
    val warningMaxKor : LiveData<Boolean> = _warningMaxKor

    private var _warningMaxEng = MutableLiveData<Boolean>()
    val warningMaxEng : LiveData<Boolean> = _warningMaxEng

    private var _backButton = MutableLiveData<Boolean>()
    val backButton : LiveData<Boolean> = _backButton

    private var _registerQues = MutableLiveData<Boolean>()
    var registerQues : LiveData<Boolean> = _registerQues

    private var _registerVisible = MutableLiveData<Boolean>()
    var registerVisible : LiveData<Boolean> = _registerVisible

    val quesEngTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            if (!p0.toString().isNullOrEmpty()) {
                val ps: Pattern = compile("^[a-zA-Z0-9\\s!~`@#\$%\\^?,. ]+$")
                _warningEng.value = !ps.matcher(p0).matches()

                _warningMaxEng.value = p0!!.length > 73

                if (_warningEng.value!! && _warningMaxEng.value!!) {
                    _warningMaxEng.value = false
                }

                if (p0!!.length <= 73 && ps.matcher(p0).matches()) {
                    _warningEng.value = false
                    _warningMaxEng.value = false
                }
                _qCreateEngQues.value = p0.toString()
                quesWarningCheck()

            } else {
                _warningEng.value = false
                _warningMaxEng.value = false
                _qCreateEngQues.value = ""
                quesWarningCheck()
            }



        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }


    val quesKorTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!p0.toString().isNullOrEmpty()) {
                val ps: Pattern = compile("^[ㄱ-ㅎㅏ-ㅣ가-힣0-9\\s!~`@#\$%\\^?,. ]+$")
                _warningKor.value = !ps.matcher(p0).matches()
                _warningMaxKor.value = p0!!.length > 45

                if (_warningKor.value!! && _warningMaxKor.value!!) {
                    _warningMaxKor.value = false
                }

                if (p0!!.length <= 45 && ps.matcher(p0).matches()) {
                    _warningKor.value = false
                    _warningMaxKor.value = false
                }
                _qCreateKorQues.value = p0.toString()
                quesWarningCheck()


            } else {
                _warningKor.value = false
                _warningMaxKor.value = false
                _qCreateKorQues.value = ""
                quesWarningCheck()

            }

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    /** 생성자 */
    init {
        _qCreateKorQues.value = ""
        _qCreateEngQues.value = ""
        _warningEng.value = false
        _warningKor.value = false
        _warningMaxEng.value = false
        _warningMaxKor.value = false
        _backButton.value = false
        _registerQues.value = false
        _registerVisible.value = false

    }
    fun quesWarningCheck() {
        Timber.e(_qCreateEngQues.value)
        if(!_qCreateEngQues.value!!.toString().isNullOrBlank()) {
            _registerVisible.value = !_warningEng.value!! && !_warningKor.value!! && !_warningMaxEng.value!! && !_warningMaxKor.value!!
        } else {
            _registerVisible.value = false
        }
    }

    fun registerClick() {
        viewModelScope.launch {
            if (_registerVisible.value!!) {
                val checkingPost = murengRepository.postCreateQuestion(PostQuestioRequest(category = "social", content = _qCreateEngQues.value.toString(), koContent = _qCreateKorQues.value.toString()!!))
                if (checkingPost) {
                    _registerQues.value = true
                }
            }
        }
    }

    fun backClick() {
        _backButton.value = true
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
