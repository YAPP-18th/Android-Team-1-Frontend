package com.engdiary.mureng.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.network.MurengService
import com.engdiary.mureng.ui.base.BaseViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kr.co.nexters.winepick.util.SharedPrefs
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


const val CONNECT_TIMEOUT = 60.toLong()
const val WRITE_TIMEOUT = 60.toLong()
const val READ_TIMEOUT = 60.toLong()

const val BASE_URL = "http://dev.mureng.hkpark.net"
const val MEDIA_BASE_URL = "http://dev.mureng-media.hkpark.net"

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
                || newUrl.contains("/api/questions")
                || newUrl.contains("/api/today-expression")
                || newUrl.contains("/api/today-question")
                || newUrl.contains("/api/member/me")
            ) {
                return@Interceptor chain.proceed(chain.request().newBuilder().apply {
                    addHeader("X-AUTH-TOKEN", authManager.test_jwt)
                    url(newUrl)
                }.build())
            }

            return@Interceptor chain.proceed(builder.build())
        }
    }

    /** HttpClient 객체를 생성하는 Provider 함수이다. */
    @Provides
    @Singleton
    fun provideHttpClient(okHttpCache: Cache, murengInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(okHttpCache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(murengInterceptor)
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
    fun provideApiService(retrofit: Retrofit): MurengService {
        return retrofit.create(MurengService::class.java)
    }
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

