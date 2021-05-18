package com.engdiary.mureng.network


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.engdiary.mureng.data.DiaryContent
import com.engdiary.mureng.data.ItemWriteDiaryImage
import com.engdiary.mureng.data.TodayQuestion
import com.engdiary.mureng.data.request.PostDiaryRequest
import com.engdiary.mureng.data.response.PostDiaryImageResponse
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.di.send
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class MurengRepository @Inject constructor(
    private val api: MurengService,
    private val authManager: AuthManager
) {
    private val IMAGE_MEDIA_TYPE = "image/jpg".toMediaType()
    private val SEAT_CHART_REQUEST_PART_KEY = "seat_url"

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

    suspend fun getTodayQuestion(): TodayQuestion? {
        return api.getTodayQuestion("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsIm5pY2tuYW1lIjoi7YWM7Iqk7Yq47Jyg7KCAIiwiaWF0IjoxNjIwODM4MTAyLCJleHAiOjE5MDAwMDAwMDB9.R9__KIcXK_MWrxc857K5IQpwoPYlEyt4eW52VsaRBDid1aFRqw8Uu_oeoserjFEjeiUmrqpAal5XvllrdNH52Q")
            .data
            ?.toDomain()
    }

    suspend fun getDiaryImages(): List<ItemWriteDiaryImage.DiaryImage> {
        return listOf(
            ItemWriteDiaryImage.DiaryImage(
                0,
                "https://mblogthumb-phinf.pstatic.net/MjAxODAxMThfMjg1/MDAxNTE2Mjc5MzQ5ODA2.uiqH6jK1ooj2toTL8zKkvjj9ERE7SyUIpImmy9h9F78g.auo0awVc4N0AS_hDCWHakBkNGsTXuMG9PFDLBlPeuycg.JPEG.studygir/Trlobtp_%283%29.jpg?type=w2",
                "/reply/0.jpg"
            ),
            ItemWriteDiaryImage.DiaryImage(
                1,
                "https://mblogthumb-phinf.pstatic.net/MjAxODAxMThfMTg4/MDAxNTE2Mjc5MzQ4NzIx.hfSUsmR1x6JGMvtkYTdLjBgLuypH_gdsGxTtHnR9H00g.MU-quSY41mihcoSGH5zBQF4ZYndfyuZkvmLbZQxx6-8g.PNG.studygir/Trlobtp_%281%29.png?type=w2",
                "/reply/1.jpg"
            ),
            ItemWriteDiaryImage.DiaryImage(
                2,
                "https://mblogthumb-phinf.pstatic.net/MjAxODAxMThfNjUg/MDAxNTE2Mjc5MzUwMjg3.CH0HqatkCmkBqDdxwloMmzK2unVgaeuLT3rwakbnt_0g.a3f8R3HoSRDIAsbe3x8wOe6jCHilwB92YSHUqQyBHb8g.PNG.studygir/Trlobtp_%281%29.png?type=w2",
                "/reply/2.jpg"
            ),
            ItemWriteDiaryImage.DiaryImage(
                3,
                "https://mblogthumb-phinf.pstatic.net/MjAxODAxMThfMjcz/MDAxNTE2Mjc5MzUxODM2.hHUtEGcoTZ_G-BO34BuX5wj0cVnw1kob2Mm3UTp9gMgg.t1wkRZZKxfwQdIEkfpmrS4hdqfBN5ZFzKaw4yhJ0Ao0g.PNG.studygir/Trlobtp_%284%29.png?type=w2",
                "/reply/3.jpg"
            ),
            ItemWriteDiaryImage.DiaryImage(
                4,
                "https://mblogthumb-phinf.pstatic.net/MjAxODAxMThfMyAg/MDAxNTE2Mjc5MzUyODc4.QMwszf0Ri_nhGEJC0eswiQ8sowS_ORwzYWvZGUVIhvEg.dLorLAERbas3SBvBE2gXKpSS6xvvrR9bebTi4woZWwIg.PNG.studygir/Trlobtp_%285%29.png?type=w2",
                "/reply/4.jpg"
            ),
            ItemWriteDiaryImage.DiaryImage(
                5,
                "https://mblogthumb-phinf.pstatic.net/MjAxODAxMThfODEg/MDAxNTE2Mjc5MzU4OTY5.g4sp4hIZcajyvJgMXIe_oDlJUvKYbUKU4mlppxm1kfwg.OWFToT3lBctlTA55iMZM6aytl816m17pK3ldegF-nIog.JPEG.studygir/Trlobtp_%2813%29.jpg?type=w2",
                "/reply/5.jpg"
            ),
            ItemWriteDiaryImage.DiaryImage(
                6,
                "https://mblogthumb-phinf.pstatic.net/MjAxODAxMThfMjkx/MDAxNTE2Mjc5NTc0MjA3.Lm9bJxxXoOoGAaxMu26ESVgMmhHcqUPHZYN98Gi_CI0g.hSPW9ibCgxg-uB9EtJuFy9VlbcwVCMYc0ox5VTTN1mAg.JPEG.studygir/Trlobtp_%2826%29.jpg?type=w2",
                "/reply/6.jpg"
            ),
            ItemWriteDiaryImage.DiaryImage(
                7,
                "https://mblogthumb-phinf.pstatic.net/MjAxODAxMThfMjkw/MDAxNTE2Mjc5NTc0ODY2.QcqF8T8awne-WDq_sRo1ed44Uh1OANE7bUOQNfuFnCUg.bqoTw_EuYZhn24fvik-6skUCxdraN03xTvFr6W1dnaUg.JPEG.studygir/Trlobtp_%2827%29.jpg?type=w2",
                "/reply/7.jpg"
            ),
        )
    }

    suspend fun postDiaryImage(imageUri: Uri?): PostDiaryImageResponse? {
        val imageBodyPart = imageUri?.let {
            buildImageMultiBodyPart(
                MurengApplication.getGlobalAppApplication(),
                SEAT_CHART_REQUEST_PART_KEY,
                it
            )
        } ?: return null

        val response = api.postDiaryImage(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsIm5pY2tuYW1lIjoi7YWM7Iqk7Yq47Jyg7KCAIiwiaWF0IjoxNjIwODM4MTAyLCJleHAiOjE5MDAwMDAwMDB9.R9__KIcXK_MWrxc857K5IQpwoPYlEyt4eW52VsaRBDid1aFRqw8Uu_oeoserjFEjeiUmrqpAal5XvllrdNH52Q",
            imageBodyPart
        ).send()
        return response.body()?.data
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

    suspend fun postDiary(diaryContent: DiaryContent, imagePath: String): Int? {
        val response =
            PostDiaryRequest(
                diaryContent.content,
                imagePath
            ).let {
                api.postDiary(
                    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsIm5pY2tuYW1lIjoi7YWM7Iqk7Yq47Jyg7KCAIiwiaWF0IjoxNjIwODM4MTAyLCJleHAiOjE5MDAwMDAwMDB9.R9__KIcXK_MWrxc857K5IQpwoPYlEyt4eW52VsaRBDid1aFRqw8Uu_oeoserjFEjeiUmrqpAal5XvllrdNH52Q",
                    it
                )
            }.send()
        return response.body()?.data?.diaryId
    }
}
