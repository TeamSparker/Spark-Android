package com.spark.android.di

import com.spark.android.data.remote.datasource.*
import com.spark.android.data.remote.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Provides
    @Singleton
    fun providesAuthDataSource(authService: AuthService): AuthDataSource =
        AuthDataSourceImpl(authService)

    @Provides
    @Singleton
    fun providesFeedDataSource(feedService: FeedService): FeedDataSource =
        FeedDataSourceImpl(feedService)

    @Provides
    @Singleton
    fun providesRemoteWaitingRoomDataSource(
        waitingRoomInfoService: WaitingRoomInfoService,
        deleteRoomService: DeleteRoomService,
        leaveRoomService: LeaveRoomService
    ): RemoteWaitingRoomDataSource =
        RemoteWaitingRoomDataSourceImpl(waitingRoomInfoService, deleteRoomService, leaveRoomService)

    @Provides
    @Singleton
    fun providesProfileDataSource(profileService: ProfileService): ProfileDataSource =
        ProfileDataSourceImpl(profileService)

    @Provides
    @Singleton
    fun providesAlarmSettingDataSource(
        alarmSettingService: AlarmSettingService
    ): AlarmSettingDataSource =
        AlarmSettingDataSourceImpl(alarmSettingService)

    @Provides
    @Singleton
    fun providesRemoteHomeDataSource(
        homeHabitRoomFinishService: HomeHabitRoomFinishService,
        homeService: HomeService
    ): RemoteHomeDataSource =
        RemoteHomeDataSourceImpl(homeHabitRoomFinishService, homeService)
}
