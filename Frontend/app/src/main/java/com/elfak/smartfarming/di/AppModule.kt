package com.elfak.smartfarming.di

import android.content.Context
import com.elfak.smartfarming.BuildConfig
import com.elfak.smartfarming.data.repositories.DeviceRepository
import com.elfak.smartfarming.data.repositories.LocalAuthRepository
import com.elfak.smartfarming.data.repositories.LocalDeviceRepository
import com.elfak.smartfarming.data.repositories.NotificationRepository
import com.elfak.smartfarming.data.repositories.RemoteAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.data.repositories.interfaces.INotificationRepository
import com.elfak.smartfarming.data.repositories.interfaces.IRemoteAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ISettingsRepository
import com.elfak.smartfarming.data.repositories.interfaces.SettingsRepository
import com.elfak.smartfarming.domain.retrofit.RetrofitClient
import com.elfak.smartfarming.domain.retrofit.apiWrappers.AuthApiWrapper
import com.elfak.smartfarming.domain.retrofit.apiWrappers.DeviceApiWrapper
import com.elfak.smartfarming.domain.retrofit.apiWrappers.NotificationApiWrapper
import com.elfak.smartfarming.domain.retrofit.apis.AuthApi
import com.elfak.smartfarming.domain.retrofit.apis.DeviceApi
import com.elfak.smartfarming.domain.retrofit.apis.NotificationApi
import com.elfak.smartfarming.domain.utils.ServiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMqttClient(
        @ApplicationContext context: Context
    ): MqttAndroidClient = MqttAndroidClient(context, BuildConfig.MQTT_URL, MqttClient.generateClientId())

    @Provides
    @Singleton
    fun provideServiceManager(
        @ApplicationContext context: Context

    ): ServiceManager = ServiceManager(context)

    @Singleton
    @Provides
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): ISettingsRepository = SettingsRepository(context)

    @Singleton
    @Provides
    fun provideLocalAuthRepository(
        @ApplicationContext context: Context,
    ): ILocalAuthRepository = LocalAuthRepository(context)

    @Singleton
    @Provides
    fun provideLocalDeviceRepository(
        @ApplicationContext context: Context,
    ): ILocalDeviceRepository = LocalDeviceRepository(context)

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

    @Singleton
    @Provides
    fun provideDeviceRepository(
        deviceApiWrapper: DeviceApiWrapper
    ): IDeviceRepository = DeviceRepository(deviceApiWrapper)

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
    fun provideDeviceApi(retrofit: Retrofit): DeviceApi = retrofit.create(DeviceApi::class.java)


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

    @Provides
    @Singleton
    fun provideDeviceApiWrapper(
        api: DeviceApi
    ): DeviceApiWrapper = DeviceApiWrapper(api)
}