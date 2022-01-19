package com.spark.android.data.remote.entity.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JoinCodeRoomInfoResponse(
    val creatorImg: String?,
    val creatorName: String,
    val profileImgs: List<String>,
    val roomId: Int,
    val roomName: String,
    val totalNums: Int
)  : Parcelable