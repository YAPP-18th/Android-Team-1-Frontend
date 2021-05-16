package com.engdiary.mureng.network


import com.engdiary.mureng.data.ItemWritingDiaryImage
import com.engdiary.mureng.data.TodayQuestion
import com.engdiary.mureng.di.AuthManager
import javax.inject.Inject

class MurengRepository @Inject constructor(
    private val api: MurengService, private val authManager: AuthManager
) {

    /** 예시
     * fun postUser(
    data: AccessTokenData,
    onSuccess: (UserData) -> Unit,
    onFailure: () -> Unit
    ) {
    api.postUser(data).safeEnqueue(
    onSuccess = { onSuccess(it!!.result!!) },
    onFailure = { onFailure() },
    onError = { onFailure() }
    )
    }
     */

    suspend fun getTodayQuestion(): TodayQuestion? {
        return api.getTodayQuestion("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsIm5pY2tuYW1lIjoi7YWM7Iqk7Yq47Jyg7KCAIiwiaWF0IjoxNjIwODM4MTAyLCJleHAiOjE5MDAwMDAwMDB9.R9__KIcXK_MWrxc857K5IQpwoPYlEyt4eW52VsaRBDid1aFRqw8Uu_oeoserjFEjeiUmrqpAal5XvllrdNH52Q")
            .data
            ?.toDomain()
    }
}
