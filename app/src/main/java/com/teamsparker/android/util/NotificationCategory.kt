package com.teamsparker.android.util

enum class NotificationCategory(val summaryId: Int, val category: String) {
    CERTIFICATION(0, "certification"),
    SPARK(1, "spark"),
    REMIND(2, "remind"),
    ROOM_START(3, "roomStart"),
    CONSIDER(4, "consider")
}
