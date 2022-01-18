package com.spark.android.di

import com.spark.android.data.remote.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
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
}
