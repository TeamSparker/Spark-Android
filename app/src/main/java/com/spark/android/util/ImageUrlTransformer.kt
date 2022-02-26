package com.spark.android.util

import java.lang.IllegalArgumentException

object ImageUrlTransformer {
    private const val SMALL_IMG_SIZE = "_270x270"
    private const val MEDIUM_IMG_SIZE = "_360x360"
    private const val BIG_IMG_SIZE = "_720x720"
    private const val JPEG = ".jpeg"
    private const val PNG = ".png"

    private fun getImageType(imageUrl: String): String = when {
        imageUrl.contains(JPEG) -> JPEG
        imageUrl.contains(PNG) -> PNG
        else -> throw IllegalArgumentException("imageUrl 이미지 확장자 에러")
    }

    fun getSmallSizeImageUrl(
        imageUrl: String,
        imageType: String = getImageType(imageUrl)
    ): String = imageUrl.replace(imageType, SMALL_IMG_SIZE + imageType)

    fun getMediumSizeImageUrl(
        imageUrl: String,
        imageType: String = getImageType(imageUrl)
    ): String = imageUrl.replace(imageType, MEDIUM_IMG_SIZE + imageType)

    fun getBigSizeImageUrl(
        imageUrl: String,
        imageType: String = getImageType(imageUrl)
    ): String = imageUrl.replace(imageType, BIG_IMG_SIZE + imageType)
}
