package com.spark.android.di

import android.content.Context
import com.spark.android.data.remote.repository.ProfileRepository
import com.spark.android.data.remote.repository.ProfileRepositoryImpl
import com.spark.android.data.remote.repository.SignInRepository
import com.spark.android.data.remote.repository.SignInRepositoryImpl
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
    fun providesSignInRepository(@ApplicationContext context: Context): SignInRepository =
        SignInRepositoryImpl(context)

    @Provides
    @Singleton
    fun providesProfileRepository(): ProfileRepository = ProfileRepositoryImpl()
}
