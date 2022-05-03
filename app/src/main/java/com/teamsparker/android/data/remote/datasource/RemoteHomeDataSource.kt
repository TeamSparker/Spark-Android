package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.HomeNoticeRedDotResponese
import com.teamsparker.android.data.remote.entity.response.HomeResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse

interface RemoteHomeDataSource {

    suspend fun getHomeAllRoom(lastId: Int, size: Int): BaseResponse<HomeResponse>

    suspend fun readFinishHabitRoom(roomId: Int): NoDataResponse

    suspend fun getHomeNoticeRedDot(): BaseResponse<HomeNoticeRedDotResponese>
}
