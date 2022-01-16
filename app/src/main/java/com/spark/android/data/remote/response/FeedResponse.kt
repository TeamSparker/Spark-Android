package com.spark.android.data.remote.response

data class FeedResponse(
    val statusCode: Int,
    val success: Boolean,
    val message: String,
    val data: FeedData
)

data class FeedData(
    val feedList: List<Feed>
)

data class Feed(
    val date: String,
    val day: String,
    val recordId: Int,
    val userId: Int,
    val nickname: String,
    val profileImg: String,
    val roomName: String,
    val certifyingImg: String,
    val likeNum: Int,
    val receivedSpark: Int,
    val isLike: Boolean,
    val timerRecord: String
)

data class FeedListItem(
    val id: String,
    val viewType: Int,
    val headerDate: String = "",
    val headerDay: String = "",
    val feed: Feed?
)
