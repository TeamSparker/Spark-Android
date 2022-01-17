package com.spark.android.di

import com.spark.android.data.remote.datasource.AuthDataSource
import com.spark.android.data.remote.datasource.AuthDataSourceImpl
import com.spark.android.data.remote.service.FeedService
import com.spark.android.data.remote.datasource.FeedDataSource
import com.spark.android.data.remote.datasource.FeedDataSourceImpl
import com.spark.android.data.remote.service.AuthService
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
}
