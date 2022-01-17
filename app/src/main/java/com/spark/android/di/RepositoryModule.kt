package com.spark.android.di

import com.spark.android.data.local.datasource.LocalPreferencesDataSource
import com.spark.android.data.remote.datasource.AuthDataSource
import com.spark.android.data.remote.repository.AuthRepository
import com.spark.android.data.remote.repository.AuthRepositoryImpl
import com.spark.android.data.remote.repository.HomeRepository
import com.spark.android.data.remote.repository.HomeRepositoryImpl
import com.spark.android.data.remote.service.HomeService
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
}


