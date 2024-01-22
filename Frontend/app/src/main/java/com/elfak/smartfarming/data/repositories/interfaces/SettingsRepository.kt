package com.elfak.smartfarming.data.repositories.interfaces

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@Singleton
class SettingsRepository @Inject constructor(
    context: Context
): ISettingsRepository {
    private val dataStore = context.settingsDataStore

    companion object {
        const val NOTIFICATION_SOUND_SETTING = "NOTIFICATION_SOUND_SETTING"
        const val REAL_TIME_DATA_SETTING = "REAL_TIME_DATA_SETTING"
    }
    override suspend fun setNotificationSoundSetting(emitSound: Boolean) {
        val key = booleanPreferencesKey(NOTIFICATION_SOUND_SETTING)
        dataStore.edit { preferences ->
            preferences[key] = emitSound
        }
    }

    override suspend fun setRealTimeDataSetting(trackData: Boolean) {
        val key = booleanPreferencesKey(REAL_TIME_DATA_SETTING)
        dataStore.edit { preferences ->
            preferences[key] = trackData
        }
    }

    override suspend fun getSoundSetting(): Boolean? {
        val key = booleanPreferencesKey(NOTIFICATION_SOUND_SETTING)
        val value = dataStore.data.map { preferences ->
            preferences[key]
        }
        return value.firstOrNull()
    }

    override suspend fun getRealTimeSetting(): Boolean? {
        val key = booleanPreferencesKey(REAL_TIME_DATA_SETTING)
        val value = dataStore.data.map { preferences ->
            preferences[key]
        }
        return value.firstOrNull()
    }
}