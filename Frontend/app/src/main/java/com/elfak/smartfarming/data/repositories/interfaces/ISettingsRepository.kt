package com.elfak.smartfarming.data.repositories.interfaces

import kotlinx.coroutines.flow.Flow

interface ISettingsRepository {
    suspend fun setNotificationSoundSetting(emitSound: Boolean)
    suspend fun setRealTimeDataSetting(trackData: Boolean)
    suspend fun getSoundSetting(): Flow<Boolean?>
    suspend fun getRealTimeSetting(): Flow<Boolean?>
}