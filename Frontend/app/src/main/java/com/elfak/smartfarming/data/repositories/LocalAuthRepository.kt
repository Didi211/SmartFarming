package com.elfak.smartfarming.data.repositories

import android.content.Context
import android.credentials.Credential
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.elfak.smartfarming.data.models.UserCredentials
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

@Singleton
class LocalAuthRepository @Inject constructor(
    context: Context
): ILocalAuthRepository {

    private val dataStore = context.dataStore
    companion object {
        const val USER_ID = "ID"
        const val USER_EMAIL = "EMAIL"
        const val USER_MQTT_TOKEN = "MQTT_TOKEN"
    }
    override suspend fun getCredentials(): UserCredentials {
        return UserCredentials(
            getLocal(USER_ID) ?: "",
            getLocal(USER_EMAIL) ?: "",
            getLocal(USER_MQTT_TOKEN) ?: ""
        )
    }

    override suspend fun setCredentials(credentials: UserCredentials) {
        saveLocal(USER_ID, credentials.id)
        saveLocal(USER_EMAIL, credentials.email)
        saveLocal(USER_MQTT_TOKEN, credentials.mqttToken)
    }

    private suspend fun saveLocal(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { auth ->
            auth[dataStoreKey] = value
        }
    }
    private suspend fun removeLocal(key: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { auth ->
            auth.remove(dataStoreKey)
        }
    }

    private suspend fun getLocal(name: String): String? {
        val key = stringPreferencesKey(name)
        val value: Flow<String?> = dataStore.data.map { preferences ->
            preferences[key]
        }
        return value.first()
    }

}