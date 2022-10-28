package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.local.datasource.LocalPreferencesHabitDataSource
import com.teamsparker.android.data.remote.calladapter.NetworkState
import com.teamsparker.android.data.remote.calladapter.RetrofitFailureStateException
import com.teamsparker.android.data.remote.datasource.HabitRoomTimeLineDataSource
import com.teamsparker.android.data.remote.entity.response.HabitRoomTimeLine
import timber.log.Timber.Forest.tag
import java.security.cert.CertificateException
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val localPreferencesHabitDataSource: LocalPreferencesHabitDataSource,
    private val habitRoomTimeLineDataSource: HabitRoomTimeLineDataSource
) : HabitRepository {

    override fun setHabitUserGuideState(state: Boolean) {
        localPreferencesHabitDataSource.setHabitUserGuideState(state)
    }

    override fun getHabitUserGuideState(): Boolean =
        localPreferencesHabitDataSource.getHabitUserGuideState()

    override suspend fun getHabitRoomTimeLine(roomId: Int): Result<HabitRoomTimeLine> {
        when (val response = habitRoomTimeLineDataSource.getHabitRoomTimeLine(roomId)) {
                    is NetworkState.Success -> return Result.success(
                        response.body.data
                    )
                    is NetworkState.Failure ->
                        if (response.code == 401) throw CertificateException("토큰 만료 오류")
                        else return Result.failure(
                            RetrofitFailureStateException(response.error, response.code)
                        )
                    is NetworkState.NetworkError -> tag("${this.javaClass.name}_getHabitRoomTimeLine")
                        .d(response.error)
                    is NetworkState.UnknownError -> tag("${this.javaClass.name}_getHabitRoomTimeLine")
                        .d(response.t)
                }
                return Result.failure(IllegalStateException("NetworkError or UnKnownError please check timber"))
    }
}
