package com.spark.android.data.remote.entity.request

import com.google.gson.annotations.SerializedName
import retrofit2.http.Multipart

data class SignUpRequest(
    @SerializedName("socialId")
    val socialId: String,
    @SerializedName("profileImg")
    val profileImg: Multipart,
    @SerializedName("nickname")
    val nickname: String
)
