package com.spark.android.data.remote.entity.response

data class FeedResponse(
    val feedList: List<Feed>
)

data class Feed(
    val certifyingImg: String,
    val date: String,
    val day: String,
    val isLiked: Boolean,
    val likeNum: Int,
    val nickname: String,
    val profileImg: String,
    val recordId: String,
    val roomName: String,
    val sparkNum: Int,
    val timerRecord: String,
    val userId: String
)

data class FeedListItem(
    val id: String,
    val viewType: Int,
    val headerDate: String = "",
    val headerDay: String = "",
    val feed: Feed?
)
