package com.elfak.smartfarming.di

import android.content.Context
import com.elfak.smartfarming.data.repositories.LocalAuthRepository
import com.elfak.smartfarming.data.repositories.RemoteAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.IRemoteAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideLocalAuthRepository(
        @ApplicationContext context: Context,
    ): ILocalAuthRepository = LocalAuthRepository(context)

    @Singleton
    @Provides
    fun provideRemoteAuthRepository(
        @ApplicationContext context: Context,
    ): IRemoteAuthRepository = RemoteAuthRepository()
}