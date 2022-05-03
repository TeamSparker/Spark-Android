package com.teamsparker.android.data.remote.entity.response

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
    val totalReceivedSpark: Int,
    val startDate: String,
    val endDate: String,
    val failDay: Int,
    val comment: Any
)



