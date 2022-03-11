package com.spark.android.di

import com.spark.android.data.remote.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {
    @Provides
    @Singleton
    fun providesAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideFeedService(retrofit: Retrofit): FeedService =
        retrofit.create(FeedService::class.java)

    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun provideMakeRoomService(retrofit: Retrofit): MakeRoomService =
        retrofit.create(MakeRoomService::class.java)

    @Provides
    @Singleton
    fun provideWaitingRoomInfoService(retrofit: Retrofit): WaitingRoomInfoService =
        retrofit.create(WaitingRoomInfoService::class.java)

    @Provides
    @Singleton
    fun provideSetPurposeService(retrofit: Retrofit): SetPurposeService =
        retrofit.create(SetPurposeService::class.java )

    @Provides
    @Singleton
    fun provideRefreshService(retrofit: Retrofit): RefreshService =
        retrofit.create(RefreshService::class.java)

    @Provides
    @Singleton
    fun provideStartHabitService(retrofit: Retrofit): StartHabitService =
        retrofit.create(StartHabitService::class.java)

    @Provides
    @Singleton
    fun provideJoinCodeRoomInfoService(retrofit: Retrofit): JoinCodeRoomInfoService =
        retrofit.create(JoinCodeRoomInfoService::class.java)

    @Provides
    @Singleton
    fun provideJoinCodeRoomDoneService(retrofit: Retrofit): JoinCodeRoomDoneService =
        retrofit.create(JoinCodeRoomDoneService::class.java)

    @Provides
    @Singleton
    fun provideDeleteRoomService(retrofit: Retrofit): DeleteRoomService =
        retrofit.create(DeleteRoomService::class.java)

    @Provides
    @Singleton
    fun provideLeaveRoomService(retrofit: Retrofit): LeaveRoomService =
        retrofit.create(LeaveRoomService::class.java)

    @Provides
    @Singleton
    fun provideHomeHabitRoomFinishService(retrofit: Retrofit): HomeHabitRoomFinishService =
        retrofit.create(HomeHabitRoomFinishService::class.java)
}
