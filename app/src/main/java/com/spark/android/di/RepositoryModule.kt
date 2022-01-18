package com.spark.android.di

import com.spark.android.data.local.datasource.LocalPreferencesDataSource
import com.spark.android.data.remote.datasource.AuthDataSource
import com.spark.android.data.remote.repository.*
import com.spark.android.data.remote.service.HomeService
import com.spark.android.data.remote.service.MakeRoomService
import com.spark.android.data.remote.service.WaitingRoomInfoService
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
    fun providesHomeRepository(
        homeService: HomeService
    ): HomeRepository =
        HomeRepositoryImpl(homeService)

    @Provides
    @Singleton
    fun providesMakeRoomRepository(
        makeRoomService: MakeRoomService
    ): MakeRoomRepository =
        MakeRoomRepositoryImpl(makeRoomService)

    @Provides
    @Singleton
    fun providesWaitingRoomInfoRepository(
        waitingRoomInfoService: WaitingRoomInfoService
    ): WaitingRoomInfoRepository =
        WaitingRoomInfoRepositoryImpl(waitingRoomInfoService)


}


