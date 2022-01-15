package com.spark.android.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spark.android.data.response.Feed
import com.spark.android.data.response.FeedListItem
import com.spark.android.ui.feed.adapter.FeedAdapter
import java.time.LocalDate

class FeedViewModel : ViewModel() {
    private val _feedList = MutableLiveData(listOf<Feed>())
    val feedList: LiveData<List<Feed>> = _feedList

    private val _feedListWithHeader = MutableLiveData(listOf<FeedListItem>())
    val feedListWithHeader: LiveData<List<FeedListItem>> = _feedListWithHeader

    fun initFeedList() {
        _feedList.value = listOf(
            Feed(
                date = "2022-01-14",
                day = "화",
                recordId = 0,
                userId = 0,
                nickname = "안드 쥬쥬",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1잔디",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:10:18"
            ),
            Feed(
                date = "2022-01-14",
                day = "화",
                recordId = 0,
                userId = 0,
                nickname = "안드 창환",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1요리",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 0,
                isLike = true,
                timerRecord = "00:20:18"
            ),
            Feed(
                date = "2022-01-14",
                day = "화",
                recordId = 0,
                userId = 0,
                nickname = "안드 재훈",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1운동",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 0,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:30:18"
            ),
            Feed(
                date = "2022-01-14",
                day = "화",
                recordId = 0,
                userId = 0,
                nickname = "안드 호재",
                profileImg = "https://picsum.photos/id/1001/5616/3744",
                roomName = "1일 1성대모사",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 5,
                receivedSpark = 9,
                isLike = true,
                timerRecord = "00:40:18"
            ),
            Feed(
                date = "2022-01-13",
                day = "월",
                recordId = 0,
                userId = 0,
                nickname = "안드 쥬쥬",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1잔디",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:10:18"
            ),
            Feed(
                date = "2022-01-13",
                day = "월",
                recordId = 0,
                userId = 0,
                nickname = "안드 창환",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1요리",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 0,
                isLike = true,
                timerRecord = "00:20:18"
            ),
            Feed(
                date = "2022-01-13",
                day = "월",
                recordId = 0,
                userId = 0,
                nickname = "안드 재훈",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1운동",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 0,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:30:18"
            ),
            Feed(
                date = "2022-01-13",
                day = "월",
                recordId = 0,
                userId = 0,
                nickname = "안드 호재",
                profileImg = "https://picsum.photos/id/1001/5616/3744",
                roomName = "1일 1성대모사",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 5,
                receivedSpark = 9,
                isLike = true,
                timerRecord = "00:40:18"
            ),
            Feed(
                date = "2022-01-12",
                day = "일",
                recordId = 0,
                userId = 0,
                nickname = "안드 쥬쥬",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1잔디",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:10:18"
            ),
            Feed(
                date = "2022-01-12",
                day = "일",
                recordId = 0,
                userId = 0,
                nickname = "안드 창환",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1요리",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 0,
                isLike = true,
                timerRecord = "00:20:18"
            ),
            Feed(
                date = "2022-01-12",
                day = "일",
                recordId = 0,
                userId = 0,
                nickname = "안드 재훈",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1운동",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 0,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:30:18"
            ),
            Feed(
                date = "2022-01-12",
                day = "일",
                recordId = 0,
                userId = 0,
                nickname = "안드 호재",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1성대모사",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 5,
                receivedSpark = 9,
                isLike = true,
                timerRecord = "00:40:18"
            ),
            Feed(
                date = "2022-01-11",
                day = "토",
                recordId = 0,
                userId = 0,
                nickname = "안드 쥬쥬",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1잔디",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:10:18"
            ),
            Feed(
                date = "2022-01-11",
                day = "토",
                recordId = 0,
                userId = 0,
                nickname = "안드 창환",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1요리",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 0,
                isLike = true,
                timerRecord = "00:20:18"
            ),
            Feed(
                date = "2022-01-11",
                day = "토",
                recordId = 0,
                userId = 0,
                nickname = "안드 재훈",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1운동",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 0,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:30:18"
            ),
            Feed(
                date = "2022-01-11",
                day = "토",
                recordId = 0,
                userId = 0,
                nickname = "안드 호재",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1성대모사",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 5,
                receivedSpark = 9,
                isLike = true,
                timerRecord = "00:40:18"
            ),
            Feed(
                date = "2022-01-10",
                day = "금",
                recordId = 0,
                userId = 0,
                nickname = "안드 쥬쥬",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1잔디",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:10:18"
            ),
            Feed(
                date = "2022-01-10",
                day = "금",
                recordId = 0,
                userId = 0,
                nickname = "안드 창환",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1요리",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 9,
                receivedSpark = 0,
                isLike = true,
                timerRecord = "00:20:18"
            ),
            Feed(
                date = "2022-01-10",
                day = "금",
                recordId = 0,
                userId = 0,
                nickname = "안드 재훈",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1운동",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 0,
                receivedSpark = 5,
                isLike = false,
                timerRecord = "00:30:18"
            ),
            Feed(
                date = "2022-01-10",
                day = "금",
                recordId = 0,
                userId = 0,
                nickname = "안드 호재",
                profileImg = "https://picsum.photos/id/0/5616/3744",
                roomName = "1일 1성대모사",
                certifyingImg = "https://picsum.photos/id/1001/5616/3744",
                likeNum = 5,
                receivedSpark = 9,
                isLike = true,
                timerRecord = "00:40:18"
            ),
        )
    }

    fun addHeaderToFeedList() {
        var date = ""
        val feedListWithHeader = mutableListOf<FeedListItem>()
        requireNotNull(feedList.value).forEachIndexed { index, feed ->
            if (feed.date != date) {
                date = feed.date
                feedListWithHeader.add(
                    FeedListItem(
                        "$index$date",
                        FeedAdapter.FEED_HEADER_TYPE,
                        formatDate(date),
                        "${feed.day}요일",
                        null
                    )
                )
            }
            feedListWithHeader.add(
                FeedListItem(
                    feed.recordId.toString(),
                    FeedAdapter.FEED_CONTENT_TYPE,
                    formatDate(date),
                    "${feed.day}요일",
                    feed
                )
            )
        }
        initFeedListWithHeader(feedListWithHeader)
    }

    private fun initFeedListWithHeader(list: List<FeedListItem>) {
        _feedListWithHeader.value = list
    }

    private fun formatDate(date: String): String {
        val dateArray = date.split("-")
        val year = dateArray[0]
        val month = dateArray[1]
        val dayOfMonth = dateArray[2]
        return "${year}년 ${month}월 ${dayOfMonth}일"
    }
}
