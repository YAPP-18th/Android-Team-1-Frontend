package com.engdiary.mureng.network

import com.engdiary.mureng.data.response.Response
import com.engdiary.mureng.data.response.TodayQuestionResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * 실제 서비스에서 사용하는 Retrofit2 인터페이스
 */
interface MurengService {

    /** 예시
     *  @GET("v2/api/survey/")
     *  fun getSurveys(): Call<WinePickResponse<List<Survey>>>
     */

    @GET("api/today-question")
    suspend fun getTodayQuestion(@Header("X-AUTH-TOKEN") accessToken: String): Response<TodayQuestionResponse>
}
