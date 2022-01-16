package com.spark.android.di

import android.content.SharedPreferences
import com.spark.android.data.local.datasource.LocalPreferencesDataSource
import com.spark.android.data.local.datasource.LocalPreferencesDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    @Provides
    @Singleton
    fun provideLocalPreferencesImpl(localPreferences: SharedPreferences): LocalPreferencesDataSource =
        LocalPreferencesDataSourceImpl(localPreferences)
}
