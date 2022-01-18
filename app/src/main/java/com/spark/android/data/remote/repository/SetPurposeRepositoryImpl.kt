package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.request.SetPurposeRequest
import com.spark.android.data.remote.entity.response.SetPurposeResponse
import com.spark.android.data.remote.service.SetPurposeService
import javax.inject.Inject

class SetPurposeRepositoryImpl @Inject constructor(
    private val setPurposeService: SetPurposeService
) : SetPurposeRepository {
    override suspend fun setPurpose(
        roomId: Int,
        body: SetPurposeRequest
    ) = setPurposeService.setPurpose(roomId, body)
}