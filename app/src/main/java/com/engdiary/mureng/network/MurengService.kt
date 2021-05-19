package com.engdiary.mureng.network

import com.engdiary.mureng.data.request.PostDiaryRequest
import com.engdiary.mureng.data.response.PostDiaryImageResponse
import com.engdiary.mureng.data.response.PostDiaryResponse
import com.engdiary.mureng.data.response.MurengResponse
import com.engdiary.mureng.data.response.TodayQuestionResponse
import okhttp3.MultipartBody
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

    @GET("/api/today-question")
    suspend fun getTodayQuestion(@Header("X-AUTH-TOKEN") accessToken: String): MurengResponse<TodayQuestionResponse>

    @POST("/api/reply/image")
    suspend fun postDiaryImage(
        @Header("X-AUTH-TOKEN") accessToken: String,
        @Part diaryImage: MultipartBody.Part,
    ): Call<MurengResponse<PostDiaryImageResponse>>

    @POST("/api/reply")
    suspend fun postDiary(
        @Header("X-AUTH-TOKEN") accessToken: String,
        @Body postDiaryRequest: PostDiaryRequest
    ): Call<MurengResponse<PostDiaryResponse>>

    @GET("/api/reply/default-images")
    suspend fun getDefaultImages(
        @Header("X-AUTH-TOKEN") accessToken: String
    ): Call<MurengResponse<List<String>>>
}
