package com.spark.android.di

import com.spark.android.data.remote.service.FeedService
import com.spark.android.data.remote.datasource.FeedDataSource
import com.spark.android.data.remote.datasource.FeedDataSourceImpl
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
    fun providesFeedDataSource(feedService: FeedService): FeedDataSource =
        FeedDataSourceImpl(feedService)
}
