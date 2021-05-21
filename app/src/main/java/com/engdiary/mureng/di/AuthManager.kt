package com.engdiary.mureng.di

import com.engdiary.mureng.constant.AuthConstant.AUTO_LOGIN_KEY
import com.engdiary.mureng.constant.AuthConstant.EXPIRE_KEY
import com.engdiary.mureng.constant.AuthConstant.ID
import com.engdiary.mureng.constant.AuthConstant.TEST_JWT
import com.engdiary.mureng.constant.AuthConstant.TOKEN_KEY
import kr.co.nexters.winepick.util.SharedPrefs
import javax.inject.Inject

/**
 *  Dagger Hilt 사용한 AuthModule (SharedPrefs - Auth 정보)
 */
class AuthManager @Inject constructor(val sharedPrefs: SharedPrefs) {
    var token: String
        get() {
            return sharedPrefs[TOKEN_KEY, ""] ?: ""
        }
        set(value) {
            sharedPrefs[TOKEN_KEY] = value
        }

    var autoLogin: Boolean
        get() {
            return sharedPrefs[AUTO_LOGIN_KEY, false] ?: false
        }
        set(value) {
            sharedPrefs[AUTO_LOGIN_KEY] = value
        }

    var expire: Long
        get() {
            return sharedPrefs[EXPIRE_KEY, 0] ?: 0
        }
        set(value) {
            sharedPrefs[EXPIRE_KEY] = value
        }

    var id: Int
        get() {
            return sharedPrefs[ID, 0] ?: 0
        }
        set(value) {
            sharedPrefs[ID] = value
        }

    var test_jwt: String
        get() {
            return sharedPrefs[TEST_JWT, ""] ?: ""
        }
        set(value) {
            sharedPrefs[TEST_JWT] = value
        }

}
