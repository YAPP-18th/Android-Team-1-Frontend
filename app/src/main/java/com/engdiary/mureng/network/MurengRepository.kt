package com.engdiary.mureng.network


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.data.DiaryContent
import com.engdiary.mureng.data.ItemWriteDiaryImage
import com.engdiary.mureng.data.Question
import com.engdiary.mureng.data.*
import com.engdiary.mureng.data.request.*
import com.engdiary.mureng.data.request.PostDiaryRequest
import com.engdiary.mureng.data.request.PostQuestioRequest
import com.engdiary.mureng.data.request.PutDiaryRequest
import com.engdiary.mureng.data.response.*
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.di.MEDIA_BASE_URL
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.util.safeEnqueue
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class MurengRepository @Inject constructor(
    private val api: MurengService,
    private val authManager: AuthManager
) {
    private val IMAGE_MEDIA_TYPE = "image/jpg".toMediaType()
    private val DIARY_IMAGE = "image"

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

    fun postKakaoLogin(
        userExistRequest: UserExistRequest,
        onSuccess: (KakaoLoginResponse) -> Unit,
        onFailure: () -> Unit
    ) {
        api.postKakaoLogin(userExistRequest).safeEnqueue(
            onSuccess = { onSuccess(it.data!!) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun getJWT(
        jwtRequest: PostJWTRequest,
        onSuccess: (JWTResponse) -> Unit,
        onFailure: () -> Unit
    ) {
        api.postJWT(jwtRequest).safeEnqueue(
            onSuccess = { onSuccess(it.data!!) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun settingUser(
        userExistRequest: UserExistRequest,
        successAction: (() -> Unit)? = null,
        failAction: (() -> Unit)? = null,
        errorAction: (() -> Unit)? = null
    ) {
        postKakaoLogin(
            userExistRequest = userExistRequest,
            onSuccess = {


                it.identifier?.let { identifier ->
                    if (it.exist) {
                        authManager.jwtIdentifier = identifier
                    } else {
                        authManager.identifier = identifier
                    }

                }

                it.identifier?.let { identifier ->
                    if (it.exist) {
                        authManager.jwtIdentifier = identifier
                    } else {
                        authManager.identifier = identifier
                    }

                    if (it.exist) {
                        getJWT(
                            jwtRequest = PostJWTRequest(identifier = authManager.jwtIdentifier),
                            onSuccess = {
                                Log.i("get JWT it.accessToken", it.accessToken)
                                authManager.accessToken = it.accessToken
                                it.refreshToken?.let {
                                    authManager.refreshToken = it
                                }
                                successAction?.let { it() }
                            },
                            onFailure = {
                                //failAction?.let { it() }
                            }
                        )

                    } else {
                        failAction?.let { it() }
                    }
                }

            },
            onFailure = { //kakao login fail
                //errorAction?.let { it() }
            }
        )
    }

    suspend fun userSignup(
        postSignupRequest: PostSignupRequest,
        successAction: (() -> Unit)? = null,
        failAction: (() -> Unit)? = null
    ) {

        val res = api.postUserSignup(postSignupRequest).body()?.data?.asDomain()
        res.let {
            authManager.jwtIdentifier = it!!.identifier
            getJWT(
                jwtRequest = PostJWTRequest(identifier = authManager.jwtIdentifier),
                onSuccess = {
                    authManager.accessToken = it.accessToken

                    it.refreshToken?.let {
                        authManager.refreshToken = it
                    }

                    successAction?.let { it() }

                },
                onFailure = {
                }
            )
        }
    }

    suspend fun getTodayQuestionRefresh(): QuestionRefresh? {
        return api.getTodayQuestionRefresh()
            .data
            ?.asDomain()
    }

    suspend fun getTodayExpression(): List<TodayExpression>? {
        return api.getTodayExpression()
            .data
    }

    suspend fun getMyScrapList(): List<TodayExpression>? {
        return api.getMyScrapList()
                .data
    }

    suspend fun getCheckRplied(): CheckReplied? {
        return api.getCheckReplied()
            .data
            ?.asDomain()
    }

    suspend fun getTodayQuestion(): Question? {
        return api.getTodayQuestion()
            .data
            ?.asDomain()
    }

    fun postScrap(
        expId: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        api.postScrap(expId).safeEnqueue(
            onSuccess = { onSuccess() },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun deleteScrap(
        expId: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        api.deleteScrap(expId).safeEnqueue(
            onSuccess = { onSuccess() },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }


    suspend fun postDiaryImage(imageUri: Uri?): String? {
        val imageBodyPart = imageUri?.let {
            buildImageMultiBodyPart(
                MurengApplication.getGlobalAppApplication(),
                DIARY_IMAGE,
                it
            )
        } ?: return null

        Timber.d("imageBodypart: $imageBodyPart")
        val response = api.postDiaryImage(
            imageBodyPart
        )

        if (!response.isSuccessful) {
            Timber.d("Post Diary Image Fail (code: ${response.code()}) (message: ${response.message()}) (raw: ${response.raw()})")
        }

        return response.body()?.data?.imagePath
    }

    private fun buildImageMultiBodyPart(
        context: Context,
        key: String,
        imageUri: Uri
    ): MultipartBody.Part {
        val imageByteArrayOutputStream = getFileByteArrayOutputStream(context, imageUri, 100)
        val imageBody = imageByteArrayOutputStream.toByteArray()
            .toRequestBody(IMAGE_MEDIA_TYPE)
        return MultipartBody.Part.createFormData(key, File(imageUri.path).name, imageBody)
    }

    private fun getFileByteArrayOutputStream(
        applicationContext: Context,
        imageUri: Uri,
        quality: Int
    ): ByteArrayOutputStream {
        val inputStream = applicationContext.contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream, null, BitmapFactory.Options())
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
        return byteArrayOutputStream
    }

    suspend fun postDiary(questionId: Int, diaryContent: DiaryContent, imagePath: String): Diary? {
        val response =
            PostDiaryRequest(
                questionId,
                diaryContent.content,
                imagePath
            ).let { api.postDiary(it) }

        if (!response.isSuccessful) {
            Timber.d("Post Diary Fail (code: ${response.code()}) (message: ${response.message()}) (response: ${response.raw()})")
        }

        return response.body()?.data?.asDomain()
    }

    suspend fun getDefaultDiaryImages(): List<ItemWriteDiaryImage.DiaryImage>? {
        val response =
            api.getDefaultImages()

        if (!response.isSuccessful) {
            Timber.d("Get Default Images Fail (code: ${response.code()}) (message: ${response.message()} (respnse: ${response.raw()})")
        }

        return response.body()
            ?.data?.mapIndexed { index, imagePath ->
                ItemWriteDiaryImage.DiaryImage(index, MEDIA_BASE_URL + imagePath, imagePath)
            }
    }

    suspend fun deleteDiary(diaryId: Int): Boolean? {
        val response = api.deleteDiary(diaryId)
            .body()
            ?.data
        return response?.isDeleted
    }

    suspend fun getQuestionList(
        page: Int,
        size: Int,
        sort: String
    ) : MurengResponse<List<QuestionNetwork>>? {
        val response = api.getQuestionList(page,size,sort)
        if(!response.isSuccessful) {
            Timber.d("Get Qustion List Fail (code: ${response.code()}) (message: ${response.message()} (respnse: ${response.raw()})")
        }
        return response?.body()
    }

    suspend fun getAnswerList(
        page: Int,
        size: Int,
        sort: String
    ) : MurengResponse<List<DiaryNetwork>>? {

        val response = api.getAnswerList(page,size,sort)
        if(!response.isSuccessful) {
            Timber.d("Get Answer List Fail (code: ${response.code()}) (message: ${response.message()} (respnse: ${response.raw()})")
        }
        return response?.body()
    }

    suspend fun getMyQuestionList(
    ) : List<Question>? {
        val response = api.getMyQuestionList()
        if(!response.isSuccessful) {
            Timber.d("Get MyQuestionList List Fail (code: ${response.code()}) (message: ${response.message()} (respnse: ${response.raw()})")
        }
        return response.body()?.data?.map {
            it.asDomain()
        }
    }

    suspend fun postCreateQuestion(
        postQuestioRequest: PostQuestioRequest
    ) : Boolean {
        val response = api.postCreateQuestion(postQuestioRequest)
        if(!response.isSuccessful) {
            Timber.d("Post Create Question Fail (code: ${response.code()}) (message: ${response.message()} (respnse: ${response.raw()})")
            return false
        }
        return true
    }

    suspend fun getReplyAnswerList(
        questionId: Int,
        page: Int?,
        size: Int?,
        sort: String?,
    ) : MurengResponse<List<DiaryNetwork>>? {

        val response = api.getReplyAnswerList(questionId, page, size, sort)
        if(!response.isSuccessful) {
            Timber.d("Get Reply Answer List Fail (code: ${response.code()}) (message: ${response.message()} (respnse: ${response.raw()})")
        }
        return response?.body()
    }

    suspend fun getMyInfo(): Author? {
        val response = api.getMyInfo()
        return response?.data?.asDomain()
    }

    suspend fun putDiary(
        diaryId: Int,
        questionId: Int,
        diaryContent: DiaryContent,
        imagePath: String
    ): Diary? {
        val response =
            api.putDiary(diaryId, PutDiaryRequest(questionId, diaryContent?.content, imagePath))
        return response.data?.asDomain()
    }

    suspend fun postLikes(
        replyId: Int
    ) : Boolean {
        val response = api.postLikes(replyId)
        if(!response.isSuccessful) {
            Timber.d("Post Like $replyId Fail (code: ${response.code()}) (message: ${response.message()} (respnse: ${response.raw()})")
            return false
        }
        return true
    }

    suspend fun deleteLikes(
        replyId: Int
    ) : Boolean {
        val response = api.deleteLikes(replyId)
        if(!response.isSuccessful) {
            Timber.d("Delete Like $replyId Fail (code: ${response.code()}) (message: ${response.message()} (respnse: ${response.raw()})")
            return false
        }
        return true
    }

    suspend fun getNickNameExist(nickName: String): NickName? {
        return api.getNickNameExist(nickName).data?.asDomain()
    }

    suspend fun putDailyPushAlertSetting(isActive: Boolean): Boolean {
        val userNetwork = api.putDailyPushAlertSetting(NotificationRequest(isActive)).data
        return userNetwork?.let {
            true
        } ?: false
    }

    suspend fun putLikeAlertSetting(isActive: Boolean): Boolean {
        val userNetwork = api.putLikeAlertSetting(NotificationRequest(isActive)).data
        return userNetwork?.let {
            true
        } ?: false
    }

    suspend fun getUserDiaries(userId: Int): List<Diary>? {
        return api.getUserDiaries(authManager.accessToken, userId)
            ?.data
            ?.map { it.asDomain() }
    }

    suspend fun postFCMToken(fcmToken: String) {
        api.postFCMToken(FcmTokenRequest(fcmToken))
    }

    suspend fun putUserFcmToken(fcmToken: String) {
        api.postUserFcmToken(FcmTokenRequest(fcmToken))
    }

    suspend fun getUserAchievement(userId: Int): Award? {
        return api.getUserAchievement(authManager.accessToken, userId)
            ?.data?.asDomain()
    }

    suspend fun withdrawMureng(): Boolean {
        val userNetwork = api.withdrawMureng().data
        return userNetwork?.let {
            authManager.expireAccessKey()
            true
        } ?: false
    }

    fun expireAccessToken() {
        authManager.expireAccessKey()
    }
}
