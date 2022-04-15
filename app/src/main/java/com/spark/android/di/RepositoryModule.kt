package com.spark.android.di

import com.spark.android.data.local.datasource.*
import com.spark.android.data.remote.datasource.AlarmCenterDataSource
import com.spark.android.data.remote.datasource.AlarmSettingDataSource
import com.spark.android.data.remote.datasource.AuthDataSource
import com.spark.android.data.remote.datasource.FeedDataSource
import com.spark.android.data.remote.datasource.ProfileDataSource
import com.spark.android.data.remote.datasource.RemoteHomeDataSource
import com.spark.android.data.remote.datasource.RemoteWaitingRoomDataSource
import com.spark.android.data.remote.repository.*
import com.spark.android.data.remote.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesAuthRepository(
        authDataSource: AuthDataSource,
        localPreferencesDataSource: LocalPreferencesDataSource
    ): AuthRepository =
        AuthRepositoryImpl(authDataSource, localPreferencesDataSource)

    @Provides
    @Singleton
    fun provideFeedRepository(
        feedDataSource: FeedDataSource
    ): FeedRepository =
        FeedRepositoryImpl(feedDataSource)

    @Provides
    @Singleton
    fun providesHomeRepository(
        remoteHomeDataSource: RemoteHomeDataSource,
        localPreferencesHomeDataSource: LocalPreferencesHomeDataSource
    ): HomeRepository =
        HomeRepositoryImpl(remoteHomeDataSource, localPreferencesHomeDataSource)

    @Provides
    @Singleton
    fun providesMakeRoomRepository(
        makeRoomService: MakeRoomService
    ): MakeRoomRepository =
        MakeRoomRepositoryImpl(makeRoomService)

    @Provides
    @Singleton
    fun providesWaitingRoomInfoRepository(
        localPreferencesWaitingRoomDataSource: LocalPreferencesWaitingRoomDataSource,
        remoteWaitingRoomDataSource: RemoteWaitingRoomDataSource
    ): WaitingRoomInfoRepository =
        WaitingRoomInfoRepositoryImpl(
            localPreferencesWaitingRoomDataSource,
            remoteWaitingRoomDataSource
        )

    @Provides
    @Singleton
    fun providesSetPurposeRepository(
        setPurposeService: SetPurposeService
    ): SetPurposeRepository =
        SetPurposeRepositoryImpl(setPurposeService)

    @Provides
    @Singleton
    fun providesRefreshRepository(
        refreshService: RefreshService
    ): RefreshRepository =
        RefreshRepositoryImpl(refreshService)

    @Provides
    @Singleton
    fun providesStartHabitRepository(
        startHabitService: StartHabitService
    ): StartHabitRepository =
        StartHabitRepositoryImpl(startHabitService)

    @Provides
    @Singleton
    fun providesJoinCodeRoomInfoRepository(
        joinCodeRoomInfoService: JoinCodeRoomInfoService
    ): JoinCodeRoomInfoRepository =
        JoinCodeRoomInfoRepositoryImpl(joinCodeRoomInfoService)

    @Provides
    @Singleton
    fun providesJoinCodeRoomDoneRepository(
        joinCodeRoomDoneService: JoinCodeRoomDoneService
    ): JoinCodeRoomDoneRepository =
        JoinCodeRoomDoneRepositoryImpl(joinCodeRoomDoneService)

    @Provides
    @Singleton
    fun providesProfileRepository(
        profileDataSource: ProfileDataSource,
        localPreferencesProfileDataSource: LocalPreferencesProfileDataSource
    ): ProfileRepository =
        ProfileRepositoryImpl(profileDataSource, localPreferencesProfileDataSource)

    @Provides
    @Singleton
    fun providesAlarmSettingRepository(
        alarmSettingDataSource: AlarmSettingDataSource,
        localPreferencesDataSource: LocalPreferencesDataSource
    ): AlarmSettingRepository =
        AlarmSettingRepositoryImpl(alarmSettingDataSource, localPreferencesDataSource)

    @Provides
    @Singleton
    fun providesAlarmCenterRepository(
        alarmCenterDataSource: AlarmCenterDataSource
    ): AlarmCenterRepository =
        AlarmCenterRepositoryImpl(alarmCenterDataSource)

    @Provides
    @Singleton
    fun providesHabitRepository(
        localPreferencesHabitDataSource: LocalPreferencesHabitDataSource
    ): HabitRepository =
        HabitRepositoryImpl(localPreferencesHabitDataSource)
}


