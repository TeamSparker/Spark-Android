package com.spark.android.di

import android.content.Context
import com.spark.android.data.remote.repository.ProfileRepository
import com.spark.android.data.remote.repository.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesProfileRepository(): ProfileRepository = ProfileRepositoryImpl()
}
