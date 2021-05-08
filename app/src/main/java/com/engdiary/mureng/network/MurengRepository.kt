package com.engdiary.mureng.network


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
}
