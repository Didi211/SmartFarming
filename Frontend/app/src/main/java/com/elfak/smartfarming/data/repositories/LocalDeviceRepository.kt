package com.elfak.smartfarming.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.deviceDataStore: DataStore<Preferences> by preferencesDataStore(name = "devices")

@Singleton
class LocalDeviceRepository @Inject constructor(
    context: Context
) : ILocalDeviceRepository {

    private val dataStore = context.deviceDataStore

    override suspend fun setIsMuted(id: String, isMuted: Boolean) {
        val deviceId = booleanPreferencesKey(id)
        dataStore.edit { device ->
            device[deviceId] = isMuted
        }
    }

    override suspend fun updateDevicesLocal(devices: List<Device>): List<Device> {
        val localDevicesMap = dataStore.data.first().asMap()
        val ids = localDevicesMap.map { it.key.toString() }

        devices.forEach { device ->
            if (!ids.contains(device.id)) {
                addDevice(device.id)
            }
        }

        ids.forEach { id ->
            if (devices.none { device -> device.id == id }) {
                removeDevice(id)
            }
        }
        return devices.map {
            it.isMuted = getIsMuted(it.id)
            it
        }
    }

    override suspend fun addDevice(id: String) {
        val deviceId = booleanPreferencesKey(id)
        dataStore.edit { device ->
            // set isMuted to false by default
            device[deviceId] = false
        }
    }

    override suspend fun removeDevice(id: String) {
        val deviceId = booleanPreferencesKey(id)
        dataStore.edit { device ->
            device.remove(deviceId)
        }
    }

    private suspend fun getIsMuted(id: String): Boolean {
        val key = booleanPreferencesKey(id)
        val value: Flow<Boolean?> = dataStore.data.map { preferences ->
            preferences[key]
        }
        return value.first()!!
    }

}