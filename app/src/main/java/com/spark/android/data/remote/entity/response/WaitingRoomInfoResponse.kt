package com.spark.android.data.remote.entity.response

data class WaitingRoomInfoResponse(
    val fromStart: Boolean,
    var members: List<Member>,
    val reqUser: ReqUser,
    val roomCode: String,
    val roomId: Int,
    val roomName: String
)

data class Member(
    val nickname: String,
    val profileImg: String?,
    val userId: Int
)

data class ReqUser(
    val isHost: Boolean,
    val isPurposeSet: Boolean,
    val moment: String,
    val nickname: String,
    val profileImg: String?,
    val purpose: String,
    val userId: Int
)
