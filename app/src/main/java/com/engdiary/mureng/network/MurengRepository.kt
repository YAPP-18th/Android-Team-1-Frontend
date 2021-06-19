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
import com.engdiary.mureng.data.response.JWTResponse
import com.engdiary.mureng.data.response.KakaoLoginResponse
import com.engdiary.mureng.data.request.PostDiaryRequest
import com.engdiary.mureng.data.request.PostQuestioRequest
import com.engdiary.mureng.data.request.PutDiaryRequest
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.MurengResponse
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.di.MEDIA_BASE_URL
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.util.safeEnqueue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
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

                it.identifier?.let {
                    authManager.jwtIdentifier = it
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
//                    failAction?.let { it() }
                }
            )
        }
    }


    suspend fun getTodayQuestion(): Question? {
        return api.getTodayQuestion()
            .data
            ?.asDomain()
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

    fun getQuestionList(
        page: Int,
        size: Int,
        sort: String,
        onSuccess: (MurengResponse<List<QuestionNetwork>>) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getQuestionList(page, size, sort).safeEnqueue(
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun getAnswerList(
        page: Int,
        size: Int,
        sort: String,
        onSuccess: (MurengResponse<List<DiaryNetwork>>) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getAnswerList(page, size, sort).safeEnqueue(
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun getMyQuestionList(
        onSuccess: (List<QuestionNetwork>) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getMyQuestionList().safeEnqueue(
            onSuccess = { onSuccess(it.data!!) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun postCreateQuestion(
        postQuestioRequest: PostQuestioRequest,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        api.postCreateQuestion(postQuestioRequest).safeEnqueue(
            onSuccess = { onSuccess() },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun getReplyAnswerList(
        questionId: Int,
        page: Int?,
        size: Int?,
        sort: String?,
        onSuccess: (MurengResponse<List<DiaryNetwork>>) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getReplyAnswerList(questionId, page, size, sort).safeEnqueue(
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
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

    fun postLikes(
        replyId: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        api.postLikes(replyId).safeEnqueue(
            onSuccess = { onSuccess() },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun deleteLikes(
        replyId: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        api.deleteLikes(replyId).safeEnqueue(
            onSuccess = { onSuccess() },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    suspend fun getNickNameExist(nickName: String): NickName? {
        return api.getNickNameExist(nickName).data?.asDomain()
    }

}
