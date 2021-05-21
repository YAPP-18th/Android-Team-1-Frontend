package com.engdiary.mureng.network

import com.engdiary.mureng.data.request.PostDiaryRequest
import com.engdiary.mureng.data.request.PostQuestioRequest
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.MurengResponse
import com.engdiary.mureng.data.response.PostDiaryImageResponse
import com.engdiary.mureng.data.response.QuestionNetwork
import okhttp3.MultipartBody
import retrofit2.Call
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
        @Header("X-AUTH-TOKEN") accessToken: String,
        @Part diaryImage: MultipartBody.Part,
    ): Response<MurengResponse<PostDiaryImageResponse>>

    @POST("/api/reply")
    suspend fun postDiary(
        @Header("X-AUTH-TOKEN") accessToken: String,
        @Body postDiaryRequest: PostDiaryRequest
    ): Response<MurengResponse<DiaryNetwork>>

    @GET("/api/reply/default-images")
    suspend fun getDefaultImages(
        @Header("X-AUTH-TOKEN") accessToken: String
    ): Response<MurengResponse<List<String>>>

    @DELETE("/api/reply/{replyId}")
    suspend fun deleteDiary(
        @Header("X-AUTH-TOKEN") accessToken: String,
        @Path("replyId") diaryId: Int
    ): Response<MurengResponse<Boolean>>


    @GET ("/api/reply")
    fun getAnswerList(
        @Query("page") page : Int,
        @Query("size") size : Int,
        @Query("sort") sort : String
    ) : Call<MurengResponse<List<DiaryNetwork>>>


    @GET ("/api/questions")
    fun getQuestionList(
        @Query("page") page : Int,
        @Query("size") size : Int,
        @Query("sort") sort : String
    ) : Call<MurengResponse<List<QuestionNetwork>>>


    @GET ("/api/questions/me")
    fun getMyQuestionList(
    ) : Call<MurengResponse<List<QuestionNetwork>>>

    @POST("/api/questions")
    fun postCreateQuestion(
        @Body postQuestioRequest: PostQuestioRequest
    ) : Call<MurengResponse<Unit>>
}
