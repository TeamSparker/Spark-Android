package com.spark.android.di

import android.content.Context
import com.spark.android.data.repository.SignInRepository
import com.spark.android.data.repository.SignInRepositoryImpl
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
}
