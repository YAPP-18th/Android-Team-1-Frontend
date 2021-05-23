package com.engdiary.mureng.network

import com.engdiary.mureng.data.request.PostDiaryRequest
import com.engdiary.mureng.data.request.PostQuestioRequest
import com.engdiary.mureng.data.response.*
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

    @GET("/api/today-question/refresh")
    suspend fun getTodayQuestionRefresh(@Header("X-AUTH-TOKEN") accessToken: String): MurengResponse<QuestionRefreshNetwork>

    @GET("/api/member/check-replied-today")
    suspend fun getCheckReplied(@Header("X-AUTH-TOKEN") accessToken: String): MurengResponse<CheckRepliedNetwork>


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

    /**
     *  답변 가져오기 (Best, Newest)
     */
    @GET ("/api/reply")
    fun getAnswerList(
        @Query("page") page : Int,
        @Query("size") size : Int,
        @Query("sort") sort : String
    ) : Call<MurengResponse<List<DiaryNetwork>>>

    /**
     *  질문 가져오기 (Best, Newest)
     */
    @GET ("/api/questions")
    fun getQuestionList(
        @Query("page") page : Int,
        @Query("size") size : Int,
        @Query("sort") sort : String
    ) : Call<MurengResponse<List<QuestionNetwork>>>

    /**
     *  내가 만든 질문 리스트 가져오기
     */
    @GET ("/api/questions/me")
    fun getMyQuestionList(
    ) : Call<MurengResponse<List<QuestionNetwork>>>

    /**
     *  질문 생성
     */
    @POST("/api/questions")
    fun postCreateQuestion(
        @Body postQuestioRequest: PostQuestioRequest
    ) : Call<MurengResponse<Unit>>

    /**
     *  질문 상세 답변 리스트 가져오기
     */
    @GET("/api/questions/{questionId}/replies")
    fun getReplyAnswerList(
        @Path("questionId") questionId: Int,
        @Query("page") page : Int?,
        @Query("size") size : Int?,
        @Query("sort") sort : String?
    ) : Call<MurengResponse<List<DiaryNetwork>>>
}
