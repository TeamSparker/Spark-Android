package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.response.NoDataResponse

interface JoinCodeRoomDoneRepository {

    suspend fun setJoinCodeRoomDone(roomId: Int): Result<NoDataResponse>
}
