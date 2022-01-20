package com.spark.android.data.remote.entity.request

import retrofit2.http.Multipart

data class CertifyRequest(
    val img: Multipart,
    val timerRecord: String? = null
)
