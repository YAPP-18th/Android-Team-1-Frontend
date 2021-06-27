package com.engdiary.mureng.network

import com.engdiary.mureng.data.request.*
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

    @POST("/api/member/user-exists/{provider}")
    fun postUserExist(
        @Path("provider") provider: String,
        @Body userExistRequest: UserExistRequest
    ): Response<MurengResponse<DiaryNetwork>>

    @POST("/api/member/user-exists/kakao")
    fun postKakaoLogin(
        @Body userExistRequest: UserExistRequest
    ): Call<MurengResponse<KakaoLoginResponse>>

    @POST("/api/jwt")
    fun postJWT(
        @Body postJWTRequest: PostJWTRequest
    ): Call<MurengResponse<JWTResponse>>

    @GET("/api/today-question")
    suspend fun getTodayQuestion(): MurengResponse<QuestionNetwork>

    @GET("/api/today-question/refresh")
    suspend fun getTodayQuestionRefresh(): MurengResponse<QuestionRefreshNetwork>

    @GET("/api/today-expression")
    suspend fun getTodayExpression(): MurengResponse<List<TodayExpression>>

    @GET("/api/member/me/scrap")
    suspend fun getMyScrapList(): MurengResponse<List<TodayExpression>>

    @GET("/api/member/check-replied-today")
    suspend fun getCheckReplied(): MurengResponse<CheckRepliedNetwork>

    @POST("/api/member/scrap/{expId}")
    fun postScrap(
            @Path("expId") expId: Int
    ): Call<MurengResponse<Unit>>

    @DELETE("/api/member/scrap/{expId}")
    fun deleteScrap(
            @Path("expId") expId: Int
    ): Call<MurengResponse<Unit>>

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
    suspend fun getMyInfo(): MurengResponse<AuthorNetwork>

    @DELETE("/api/reply/{replyId}")
    suspend fun deleteDiary(
        @Path("replyId") diaryId: Int
    ): Response<MurengResponse<DeleteDiaryResponse>>

    /**
     *  답변 가져오기 (Best, Newest)
     */
    @GET("/api/reply")
    suspend fun getAnswerList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Response<MurengResponse<List<DiaryNetwork>>>

    /**
     *  질문 가져오기 (Best, Newest)
     */
    @GET("/api/questions")
    suspend fun getQuestionList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Response<MurengResponse<List<QuestionNetwork>>>

    /**
     *  내가 만든 질문 리스트 가져오기
     */
    @GET("/api/questions/me")
    suspend fun getMyQuestionList(
    ): Response<MurengResponse<List<QuestionNetwork>>>

    /**
     *  질문 생성
     */
    @POST("/api/questions")
    suspend fun postCreateQuestion(
        @Body postQuestioRequest: PostQuestioRequest
    ): Response<MurengResponse<Unit>>

    /**
     *  질문 상세 답변 리스트 가져오기
     */
    @GET("/api/questions/{questionId}/replies")
    suspend fun getReplyAnswerList(
        @Path("questionId") questionId: Int,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?
    ): Response<MurengResponse<List<DiaryNetwork>>>

    @POST("/api/member/signup")
    suspend fun postUserSignup(
        @Body postSignupRequest: PostSignupRequest
    ): Response<MurengResponse<UserNetwork>>

    @GET("/api/member/nickname-exists/{nickname}")
    suspend fun getNickNameExist(
        @Path("nickname") nickname: String
    ): MurengResponse<NickNameNetwork>

    @PUT("/api/reply/{replyId}")
    suspend fun putDiary(
        @Path("replyId") replyId: Int?,
        @Body putDiaryRequest: PutDiaryRequest
    ): MurengResponse<DiaryNetwork>

    /**
     * 답변 좋아요
     */
    @POST("/api/reply/{replyId}/reply-likes")
    suspend fun postLikes(
        @Path("replyId") replyId: Int
    ): Response<MurengResponse<Unit>>

    /**
     * 답변 좋아요 취소
     */
    @DELETE("/api/reply/{replyId}/reply-likes")
    suspend fun deleteLikes(
        @Path("replyId") replyId: Int
    ): Response<MurengResponse<Unit>>

    @PUT("api/member/me/setting/push/daily")
    suspend fun putDailyPushAlertSetting(
        @Body notificationRequest: NotificationRequest
    ): MurengResponse<UserNetwork>

    @PUT("/api/member/me/setting/push/like")
    suspend fun putLikeAlertSetting(
        @Body notificationRequest: NotificationRequest
    ): MurengResponse<UserNetwork>

    @GET("/api/member/{memberId}/replies")
    suspend fun getUserDiaries(
        @Header("X-AUTH-TOKEN") accessToken: String,
        @Path("memberId") userId: Int
    ): MurengResponse<List<DiaryNetwork>>

    @POST("/api/fcm-token")
    suspend fun postFCMToken(
        @Body fcmTokenRequest: FcmTokenRequest
    ): MurengResponse<AuthorNetwork>

    @PUT("/api/member/me/fcm-token")
    suspend fun postUserFcmToken(
        @Body fcmTokenRequest: FcmTokenRequest
    ): MurengResponse<AuthorNetwork>


    @GET("/api/member/{memberId}/achievement")
    suspend fun getUserAchievement(
        @Header("X-AUTH-TOKEN") accessToken: String,
        @Path("memberId") userId: Int
    ) : MurengResponse<AwardNetwork>

    @DELETE("/api/member/me")
    suspend fun withdrawMureng(): MurengResponse<UserNetwork>
}
