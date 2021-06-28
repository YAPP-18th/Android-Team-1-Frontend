package com.engdiary.mureng.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.engdiary.mureng.data.request.PostRefreshAccessTokenRequest
import com.engdiary.mureng.data.response.PostRefreshAccessTokenResponse
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.network.MurengService
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kr.co.nexters.winepick.util.SharedPrefs
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


const val CONNECT_TIMEOUT = 60.toLong()
const val WRITE_TIMEOUT = 60.toLong()
const val READ_TIMEOUT = 60.toLong()

const val BASE_URL = "https://mureng.hkpark.net"
const val MEDIA_BASE_URL = "https://mureng-media.hkpark.net"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokenInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenInterceptor


/**
 * 코루틴을 활용하여 HTTP 요청을 보낼 시 활용하는 로직
 * 코루틴을 활용할 경우, onFailure 에서 보내는 exception 내용에 따라 로직 작업을 수행한다.
 */
suspend fun <T> Call<T>.send(): Response<T> = suspendCoroutine {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            it.resume(response)
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            it.resumeWithException(throwable)
        }
    })
}

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun provideViewModelFactory(
        murengRepository: MurengRepository
    ): ViewModelProvider.AndroidViewModelFactory = ViewModelFactoryImpl(
        MurengApplication.getGlobalAppApplication(), murengRepository
    )

    /**
     * ViewModelFactory 구현체 (impl) 를 만드는 클래스
     */
    class ViewModelFactoryImpl(
        val murengApplication: MurengApplication,
        val murengRepository: MurengRepository
    ) : ViewModelProvider.AndroidViewModelFactory(murengApplication) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BaseViewModel(murengRepository) as T
        }
    }
}

@GlideModule
class MurengGlide : AppGlideModule()

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (com.engdiary.mureng.BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(): Cache {
        // 10MB
        val cacheSize = 10 * 1024 * 1024L
        return Cache(MurengApplication.getGlobalApplicationContext().cacheDir, cacheSize)
    }

    /**
     * 커스텀 interceptor
     */
    @TokenInterceptor
    @Provides
    @Singleton
    fun provideMurengInterceptor(authManager: AuthManager): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            var newUrl = request.url.toString()
            val builder = chain.request().newBuilder()
                .url(newUrl)

            if (newUrl.contains("/api/reply")
                || newUrl.contains("/api/member/check-replied-today")
                || newUrl.contains("/api/member/scrap")
                || newUrl.contains("/api/questions")
                || newUrl.contains("/api/today-expression")
                || newUrl.contains("/api/today-question")
                || newUrl.contains("/api/member/me")
                || newUrl.contains("/api/member/me/fcm-token")
            ) {
                return@Interceptor chain.proceed(chain.request().newBuilder().apply {
                    addHeader("X-AUTH-TOKEN", authManager.accessToken)
                    url(newUrl)
                }.build())
            }

            return@Interceptor chain.proceed(builder.build())
        }
    }


    @RefreshTokenInterceptor
    @Provides
    @Singleton
    fun provideAuthenticator(
        serviceHolder: ServiceHolder,
        authManager: AuthManager
    ): Authenticator {
        return object : Authenticator {
            override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
                val tokenRefreshResponse =
                    serviceHolder.service!!
                        .postRefreshAccessToken(PostRefreshAccessTokenRequest(authManager.refreshToken))
                        .execute()

                return if (tokenRefreshResponse.isSuccessful) {
                    saveToken(authManager, tokenRefreshResponse.body()?.data!!)
                    response.request
                        .newBuilder()
                        .header("X-AUTH-TOKEN", authManager.accessToken)
                        .build()
                } else {
                    response.request
                }
            }
        }
    }

    private fun saveToken(authManager: AuthManager, response: PostRefreshAccessTokenResponse) {
        authManager.refreshToken = response.refreshToken
        authManager.accessToken = response.accessToken
    }

    /** HttpClient 객체를 생성하는 Provider 함수이다. */
    @Provides
    @Singleton
    fun provideHttpClient(
        okHttpCache: Cache,
        @TokenInterceptor murengInterceptor: Interceptor,
        @RefreshTokenInterceptor authenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(okHttpCache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(murengInterceptor)
            .authenticator(authenticator)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /** Retrofit 객체를 생성하는 Provider 함수이다. */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit, serviceHolder: ServiceHolder): MurengService {
        return retrofit.create(MurengService::class.java)
            .also { serviceHolder.service = it }
    }

    @Provides
    @Singleton
    fun provideServiceHolder(): ServiceHolder = ServiceHolder()
}

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideSharedPrefs(): SharedPrefs {
        return SharedPrefs()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthManager(sharedPrefs: SharedPrefs): AuthManager {
        return AuthManager(sharedPrefs)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideWinePickRepository(
        murengService: MurengService,
        authManager: AuthManager
    ): MurengRepository {
        return MurengRepository(murengService, authManager)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
}

@Module
@InstallIn(ServiceComponent::class)
class ServiceModule {
    @Provides
    fun provideIoCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}
