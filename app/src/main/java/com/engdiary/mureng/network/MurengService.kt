package com.engdiary.mureng.network

import com.engdiary.mureng.data.request.PostDiaryRequest
import com.engdiary.mureng.data.response.*
import okhttp3.MultipartBody
import retrofit2.Response
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
    suspend fun getTodayQuestion(@Header("X-AUTH-TOKEN") accessToken: String): MurengResponse<QuestionNetwork>

    @Multipart
    @POST("/api/reply/image")
    suspend fun postDiaryImage(
        @Part diaryImage: MultipartBody.Part
    ): Response<MurengResponse<PostDiaryImageResponse>>

    @POST("/api/reply")
    suspend fun postDiary(
        @Body postDiaryRequest: PostDiaryRequest
    ): Response<MurengResponse<DiaryNetwork>>

    @GET("/api/reply/default-images")
    suspend fun getDefaultImages(): Response<MurengResponse<List<String>>>

    @GET("/api/member/me")
    suspend fun getMyInfo(
        @Header("X-AUTH-TOKEN") accessToken: String
    ): Response<MurengResponse<AuthorNetwork>>
}
