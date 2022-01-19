package com.spark.android.data.remote.entity.response


import com.google.gson.annotations.SerializedName

data class StorageResponse(
    val nickname: String,
    val totalRoomNum: Int,
    val ongoingRoomNum: Int,
    val completeRoomNum: Int,
    val failRoomNum: Int,
    val rooms: List<StorageRoom>
)

data class StorageRoom(
    val roomId: Int,
    val roomName: String,
    val leftDay: Int,
    val thumbnail: String,
    val totalRecievedSpark: Int,
    val startDate: String,
    val endDate: String,
    val failDay: Int,
    val comment: Any
)



