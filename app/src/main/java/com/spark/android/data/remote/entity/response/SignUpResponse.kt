package com.spark.android.data.remote.entity.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("accesstoken")
    val accessToken: String,
    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profileImg")
    val profileImg: String?,
    @SerializedName("userId")
    val userId: Int
)
