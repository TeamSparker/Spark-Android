package com.spark.android.util

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

fun getImgUri(contentResolver: ContentResolver): Uri? {
    val folderName = "Spark"
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val picturePath = "${Environment.DIRECTORY_PICTURES}${File.separator}$folderName"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpeg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, picturePath)
        }
    }

    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

fun useBitmapImg(context: Context, imgUrl: String, useBitmap: (Bitmap) -> Unit) {
    Glide.with(context)
        .asBitmap()
        .load(imgUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                useBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

fun getPathFromUri(context: Context, uri: Uri): String {
    val cursor: Cursor = context.contentResolver.query(uri, null, null, null, null)
        ?: throw NullPointerException()
    cursor.moveToNext()
    val columnIndex = cursor.getColumnIndex("_data")
    val path = if (columnIndex >= 0) {
        cursor.getString(columnIndex)
    } else {
        throw IllegalAccessException()
    }
    cursor.close()
    return path
}
