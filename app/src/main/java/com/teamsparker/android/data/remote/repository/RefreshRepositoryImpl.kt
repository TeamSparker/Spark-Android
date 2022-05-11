package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.service.RefreshService
import javax.inject.Inject

class RefreshRepositoryImpl @Inject constructor(
    private val refreshService: RefreshService
) : RefreshRepository{
    override suspend fun getRefresh(
        roomId: Int
    ) = kotlin.runCatching {
        refreshService.getRefresh(roomId)
    }
}
