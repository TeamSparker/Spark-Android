package com.spark.android.ui.auth.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import dagger.hilt.android.qualifiers.ActivityContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class MultiPartResolver @Inject constructor(
    @ActivityContext private val context: Context
) {
    fun createImgMultiPart(uri: Uri): MultipartBody.Part {
        val options = BitmapFactory.Options()
        val inputStream = context.contentResolver.openInputStream(uri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        BitmapFactory.decodeStream(inputStream, null, options)
            ?.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
        val file = File(uri.toString())
        val surveyBody =
            byteArrayOutputStream.toByteArray().toRequestBody("image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, surveyBody)
    }

    fun createImgMultiPart(bitmap: Bitmap): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val file = File(bitmap.toString())
        val surveyBody =
            byteArrayOutputStream.toByteArray().toRequestBody("image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, surveyBody)
    }
}
