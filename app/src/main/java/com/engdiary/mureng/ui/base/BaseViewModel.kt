package com.engdiary.mureng.ui.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.data.SingleLiveEvent
import com.engdiary.mureng.data.request.KakaoLoginRequest
import com.engdiary.mureng.data.request.UserExistRequest
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.network.MurengRepository

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * BaseViewModel
 */
open class BaseViewModel @Inject constructor(
    private val murengRepository: MurengRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _navigateToSignup = SingleLiveEvent<Unit>()
    val navigateToSignup: LiveData<Unit>
        get() = _navigateToSignup

    private val _navigateToHome = SingleLiveEvent<Unit>()
    val navigateToHome: LiveData<Unit>
        get() = _navigateToHome



    /** viewModelScope 에서 Exception 이 발생할 시 처리하는 핸들러 */
    val vmExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.i("$coroutineContext $throwable")
    }

    /** 생성자 개념으로 생각하면 편할듯 */
    init {
        /**
         * viewModelScope 를 사용하면 lifecycle 을 인식하는 CoroutineScope 를 만들 수 있다.
         * viewModelScope 블록에서 실행되는 작업은 별도의 처리를 하지 않아도
         * ViewModel 이 clear 되는 순간 자동으로 취소된다.
         *
         * @see [[Android & Coroutine] ViewModelScope, LiveData Builder 사용하기](https://zion830.tistory.com/64)
         */
        viewModelScope.launch {}
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    /** 카카오 로그인 서버 통신 */
    open fun addKakaoUser(accessToken: String, refreshToken: String, userId: Long) {
        murengRepository.settingUser(
            userExistRequest =  UserExistRequest(accessToken = accessToken, refreshToken = refreshToken),
            successAction =  {
                Log.i("TKAKAEE", "T")
                _navigateToHome.call()
            },
            failAction =  {
                Log.i("TKAKA", "T")

                _navigateToSignup.call()

//                val test = Intent(MurengApplication.appContext, SignupTermsActivity::class.java).apply {
//                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }



                Log.i("TKAKA", "T")

//                _toastMeesageText.value = PerfumeApplication.getGlobalApplicationContext()
//                    .resources.getString(R.string.api_error)
            })
    }

    /**
     * [Disposable] 객체를 [compositeDisposable] 에 넣는다.
     * [compositeDisposable] 에 추가된 [Disposable] 들은
     * [onCleared] 단계에서 모두 clear 된다.
     */
    protected fun Disposable.addDisposable() {
        compositeDisposable.add(this)
    }

    /**
     * UI 단계에서 onResume 일 시 실행해주어야 하는 로직을 명세한다.
     * 필수가 아니므로 추상화는 하지 않는다.
     */
    open fun onResume() {

    }

    open fun onAttach(context: Context) {

    }
}
