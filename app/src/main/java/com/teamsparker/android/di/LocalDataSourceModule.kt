package com.teamsparker.android.di

import android.content.SharedPreferences
import com.teamsparker.android.data.local.datasource.*
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

    @Provides
    @Singleton
    fun provideLocalPreferencesWaitingRoomDataSource(localPreferences: SharedPreferences): LocalPreferencesWaitingRoomDataSource =
        LocalPreferencesWaitingRoomDataSourceImpl(localPreferences)

    @Provides
    @Singleton
    fun provideLocalPreferencesHomeDataSource(localPreferences: SharedPreferences): LocalPreferencesHomeDataSource =
        LocalPreferencesHomeDataSourceImpl(localPreferences)

    @Provides
    @Singleton
    fun provideLocalPreferencesHabitDataSource(localPreferences: SharedPreferences): LocalPreferencesHabitDataSource =
        LocalPreferencesHabitDataSourceImpl(localPreferences)

    @Provides
    @Singleton
    fun provideLocalPreferencesProfileDataSource(localPreferences: SharedPreferences): LocalPreferencesProfileDataSource =
        LocalPreferencesProfileDataSourceImpl(localPreferences)
}
