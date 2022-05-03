package com.teamsparker.android.util

import android.graphics.Bitmap

object ImageCropUtil {
    fun squareCropBitmap(bitmap: Bitmap): Bitmap {
        val originWidth = bitmap.width
        val originHeight = bitmap.height
        var x = 0
        var y = 0
        var resizedLength = 0
        if (originWidth > originHeight) {
            resizedLength = originHeight
            x = (originWidth - resizedLength) / 2
        } else {
            resizedLength = originWidth
            y = (originHeight - resizedLength) / 2
        }
        return Bitmap.createBitmap(bitmap, x, y, resizedLength, resizedLength)
    }
}
