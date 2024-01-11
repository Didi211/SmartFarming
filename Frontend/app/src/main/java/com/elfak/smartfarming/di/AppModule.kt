package com.elfak.smartfarming.di

import android.content.Context
import com.elfak.smartfarming.data.repositories.LocalAuthRepository
import com.elfak.smartfarming.data.repositories.NotificationRepository
import com.elfak.smartfarming.data.repositories.RemoteAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.INotificationRepository
import com.elfak.smartfarming.data.repositories.interfaces.IRemoteAuthRepository
import com.elfak.smartfarming.domain.retrofit.RetrofitClient
import com.elfak.smartfarming.domain.retrofit.apiWrappers.AuthApiWrapper
import com.elfak.smartfarming.domain.retrofit.apiWrappers.NotificationApiWrapper
import com.elfak.smartfarming.domain.retrofit.apis.AuthApi
import com.elfak.smartfarming.domain.retrofit.apis.NotificationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
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
        authApiWrapper: AuthApiWrapper
    ): IRemoteAuthRepository = RemoteAuthRepository(authApiWrapper)

    @Singleton
    @Provides
    fun provideNotificationRepository(
        notificationApiWrapper: NotificationApiWrapper
    ): INotificationRepository = NotificationRepository(notificationApiWrapper)

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = RetrofitClient.googleRoutesApiClient()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideNotificationApi(retrofit: Retrofit): NotificationApi = retrofit.create(NotificationApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApiWrapper(
        api: AuthApi
    ): AuthApiWrapper = AuthApiWrapper(api)

    @Provides
    @Singleton
    fun provideNotificationApiWrapper(
        api: NotificationApi
    ): NotificationApiWrapper = NotificationApiWrapper(api)
}