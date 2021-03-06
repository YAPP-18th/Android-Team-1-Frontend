package com.engdiary.mureng.ui.base

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.engdiary.mureng.di.AuthManager
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

/**
 * BaseActivity
 *
 * @param layoutResId 레이아웃 Resource Id
 *
 * @property binding 바인딩 객체
 * @property viewModel ViewModel 객체
 * @property viewModelFactory ViewModel 을 사용하기 위해 반드시 필요한 ViewModelFactory
 *
 */


abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    protected lateinit var binding: B

    /**
     * [Dispatchers.Main]을 기본으로 사용하고
     * [onDestroy]에서 [cancel][CoroutineScope.cancel] 되는 코루틴 스코프
     *
     * @see [Coroutine 공식문서 Coroutine scope](https://kotlinlang.org/docs/reference/coroutines/coroutine-context-and-dispatchers.html#coroutine-scope)
     */
    val uiScope: CoroutineScope = MainScope()

    /**
     * [startActivityForResult] 로부터 결과가 올 때까지
     * thread 를 방해하지 않고 결과를 기다리는 [CompletableDeferred] 객체
     *
     * complete or cancel 을 통하여 일을 마칠 수 있으며 자세한 내용은 [CompletableDeferred] 참고
     */
    internal var deferred = CompletableDeferred<ActivityResult>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    abstract val viewModel: BaseViewModel?

    @Inject
    lateinit var authManager: AuthManager
    internal val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e("로그인 실패- $error")
        } else if (token != null) {
            UserApiClient.instance.me { user, error ->
                val kakaoId = user!!.id
                Timber.d("token.accessToken- $token.accessToken.toString()")
                Timber.d("token.refreshToken $token.refreshToken.toString()")
                Timber.d("kakao email $user!!.kakaoAccount!!.email.toString()")

                viewModel?.addKakaoUser(token.accessToken, token.refreshToken, kakaoId)
            }
            Timber.d("로그인성공 - 토큰 ${authManager.accessToken}")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this

        /** [uiScope] 사용 예 */
        uiScope.launch { }

    }

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.i("requestCode : $requestCode, resultCode : $resultCode, intent : $intent")
        deferred.complete(ActivityResult(resultCode, data))
    }

    override fun onDestroy() {
        uiScope.cancel()
        super.onDestroy()
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
    }

    /**
     * startActivityForResult 를 코루틴 형태로 실행한다.
     *
     * @param intent 실행시킬 intent
     * @param requestCode requestCode
     *
     * @return 각 화면의 [onActivityResult] 에서 complete 또는 cancel 처리해야 할 [Deferred] 객체
     * */
    suspend fun startActivity(
        intent: Intent,
        requestCode: Int = -1
    ): ActivityResult {
        // 이전에 중단되었던 작업이 있는 경우 cancel 시켜준다.
        if (deferred.isActive) deferred.cancel()

        deferred = CompletableDeferred()

        if (requestCode < 0)
            startActivity(intent)
        else
            startActivityForResult(intent, requestCode)

        return deferred.await()
    }
}
