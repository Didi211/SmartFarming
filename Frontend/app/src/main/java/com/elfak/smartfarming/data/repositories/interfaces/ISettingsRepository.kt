package com.elfak.smartfarming.data.repositories.interfaces

interface ISettingsRepository {
    suspend fun setNotificationSoundSetting(emitSound: Boolean)
    suspend fun setRealTimeDataSetting(trackData: Boolean)
    suspend fun getSoundSetting(): Boolean?
    suspend fun getRealTimeSetting(): Boolean?
}