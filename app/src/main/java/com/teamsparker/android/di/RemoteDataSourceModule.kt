package com.teamsparker.android.di

import com.teamsparker.android.data.remote.datasource.*
import com.teamsparker.android.data.remote.service.*
import dagger.Binds
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
        homeService: HomeService,
        homeNoticeRedDotService: HomeNoticeRedDotService
    ): RemoteHomeDataSource =
        RemoteHomeDataSourceImpl(homeHabitRoomFinishService, homeService, homeNoticeRedDotService)

    @Provides
    @Singleton
    fun provideAlarmCenterDataSource(
        alarmCenterService: AlarmCenterService
    ): AlarmCenterDataSource =
        AlarmCenterDataSourceImpl(alarmCenterService)

    @Binds
    @Singleton
    fun provideHabitRoomTimeLineDataSource(
        habitRoomTimeLineService: HabitRoomTimeLineService
    ): HabitRoomTimeLineDataSource =
        HabitRoomTImeLineDataSourceImpl(habitRoomTimeLineService)
}
