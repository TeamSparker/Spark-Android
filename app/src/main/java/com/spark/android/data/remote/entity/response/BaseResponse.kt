package com.spark.android.data.remote.entity.response

data class BaseResponse<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val `data`: T?
)
