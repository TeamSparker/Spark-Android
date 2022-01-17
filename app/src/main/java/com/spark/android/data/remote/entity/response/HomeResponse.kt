package com.spark.android.data.remote.entity.response

data class HomeResponse(
    val rooms: List<Room>
)

data class Room(
    val doneMemberNum: Int,
    val isDone: Boolean,
    val isStarted: Boolean,
    val leftDay: Int?,
    val life: Int,
    val memberNum: Int,
    val profileImg: List<String>,
    val roomId: Int,
    val roomName: String
)
