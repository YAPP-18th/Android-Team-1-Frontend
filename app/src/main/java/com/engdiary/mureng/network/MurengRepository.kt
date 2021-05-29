package com.engdiary.mureng.network


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.engdiary.mureng.data.*
import com.engdiary.mureng.data.request.PostDiaryRequest
import com.engdiary.mureng.data.request.PostQuestioRequest
import com.engdiary.mureng.data.request.PutDiaryRequest
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.di.BASE_URL
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

    suspend fun getTodayQuestion(): Question? {
        return api.getTodayQuestion("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsIm5pY2tuYW1lIjoi7YWM7Iqk7Yq47Jyg7KCAIiwiaWF0IjoxNjIwODM4MTAyLCJleHAiOjE5MDAwMDAwMDB9.R9__KIcXK_MWrxc857K5IQpwoPYlEyt4eW52VsaRBDid1aFRqw8Uu_oeoserjFEjeiUmrqpAal5XvllrdNH52Q")
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

    suspend fun postDiary(diaryContent: DiaryContent, imagePath: String): Diary? {
        val response =
            PostDiaryRequest(
                diaryContent.content,
                imagePath
            ).let {
                api.postDiary(
                    it
                )
            }

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
                ItemWriteDiaryImage.DiaryImage(index, BASE_URL + imagePath, imagePath)
            }
    }

    suspend fun deleteDiary(diaryId: Int): Boolean? {
        val response = api.deleteDiary(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsIm5pY2tuYW1lIjoi7YWM7Iqk7Yq47Jyg7KCAIiwiaWF0IjoxNjIwODM4MTAyLCJleHAiOjE5MDAwMDAwMDB9.R9__KIcXK_MWrxc857K5IQpwoPYlEyt4eW52VsaRBDid1aFRqw8Uu_oeoserjFEjeiUmrqpAal5XvllrdNH52Q",
            diaryId
        )
        return response.body()?.data
    }

    fun getQuestionList(
        page: Int,
        size: Int,
        sort: String,
        onSuccess: (List<QuestionNetwork>) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getQuestionList(page, size, sort).safeEnqueue(
            onSuccess = { onSuccess(it.data!!) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun getAnswerList(
        page: Int,
        size: Int,
        sort: String,
        onSuccess: (List<DiaryNetwork>) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getAnswerList(page, size, sort).safeEnqueue(
            onSuccess = { onSuccess(it.data!!) },
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
        onSuccess: (List<DiaryNetwork>) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getReplyAnswerList(questionId, page, size, sort).safeEnqueue(
            onSuccess = { onSuccess(it.data!!) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    suspend fun getMyInfo(): Author? {
        val response = api.getMyInfo(authManager.token)
        return response.body()?.data?.asDomain()
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
}
