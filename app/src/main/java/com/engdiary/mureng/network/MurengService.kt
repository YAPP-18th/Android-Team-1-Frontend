package com.engdiary.mureng.network

import com.engdiary.mureng.data.request.PostDiaryRequest
import com.engdiary.mureng.data.request.PostQuestioRequest
import com.engdiary.mureng.data.request.PutDiaryRequest
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

    @DELETE("/api/reply/{replyId}")
    suspend fun deleteDiary(
        @Path("replyId") diaryId: Int
    ): Response<MurengResponse<DeleteDiaryResponse>>

    /**
     *  답변 가져오기 (Best, Newest)
     */
    @GET("/api/reply")
    fun getAnswerList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Call<MurengResponse<List<DiaryNetwork>>>

    /**
     *  질문 가져오기 (Best, Newest)
     */
    @GET("/api/questions")
    fun getQuestionList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Call<MurengResponse<List<QuestionNetwork>>>

    /**
     *  내가 만든 질문 리스트 가져오기
     */
    @GET("/api/questions/me")
    fun getMyQuestionList(
    ): Call<MurengResponse<List<QuestionNetwork>>>

    /**
     *  질문 생성
     */
    @POST("/api/questions")
    fun postCreateQuestion(
        @Body postQuestioRequest: PostQuestioRequest
    ): Call<MurengResponse<Unit>>

    /**
     *  질문 상세 답변 리스트 가져오기
     */
    @GET("/api/questions/{questionId}/replies")
    fun getReplyAnswerList(
        @Path("questionId") questionId: Int,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?
    ): Call<MurengResponse<List<DiaryNetwork>>>

    @PUT("/api/reply/{replyId}")
    suspend fun putDiary(
        @Path("replyId") replyId: Int?,
        @Body putDiaryRequest: PutDiaryRequest
    ): MurengResponse<DiaryNetwork>

    /**
     * 답변 좋아요
     */
    @POST("/api/reply/{replyId}/reply-likes")
    fun postLikes(
            @Path("replyId") replyId : Int
    ) : Call<MurengResponse<Unit>>

    /**
     * 답변 좋아요 취소
     */
    @DELETE("/api/reply/{replyId}/reply-likes")
    fun deleteLikes(
            @Path("replyId") replyId: Int
    ) : Call<MurengResponse<Unit>>

}
