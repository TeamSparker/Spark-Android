package com.spark.android.util

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun getImgUri(contentResolver: ContentResolver): Uri? {
    val folderName = "Spark"
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val picturePath = "${Environment.DIRECTORY_PICTURES}${File.separator}$folderName"
    val contentValues = ContentValues()
    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpeg")
    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, picturePath)

    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}
