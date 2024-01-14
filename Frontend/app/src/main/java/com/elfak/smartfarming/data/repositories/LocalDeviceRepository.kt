package com.elfak.smartfarming.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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
        var device = getDevice(id)
            ?: throw Exception("Device with ID [${id}] not found in local storage.")
        device.isMuted = isMuted
        updateDevice(device)
    }

    override suspend fun updateDevicesLocal(devices: List<Device>): List<Device> {
        val localDevicesMap = dataStore.data.first().asMap()
        val ids = localDevicesMap.map { it.key.toString() }

        // adding new devices
        devices.forEach { device ->
            if (!ids.contains(device.id)) {
                addDevice(device)
            }
        }

        // removing invalid devices
        ids.forEach { id ->
            if (devices.none { device -> device.id == id }) {
                removeDevice(id)
            }
        }
        return devices.map {
            val localDevice = getDevice(it.id)
            it.isMuted = localDevice!!.isMuted
            it.lastReading = localDevice.lastReading
            it
        }
    }

    override suspend fun addDevice(device: Device) {
        val deviceId = stringPreferencesKey(device.id)
        dataStore.edit { preferences ->
            device.isMuted = false
            val deviceJson = Gson().toJson(device)
            preferences[deviceId] = deviceJson
        }
    }

    override suspend fun removeDevice(id: String) {
        val deviceId = stringPreferencesKey(id)
        dataStore.edit { device ->
            device.remove(deviceId)
        }
    }

    override suspend fun setRealTimeData(id: String, lastReading: Double) {
        var device = getDevice(id)
            ?: throw Exception("Device with ID [${id}] not found in local storage.")
        if (device.type != DeviceTypes.Sensor) {
            throw Exception("Device ${device.name} is not ${DeviceTypes.Sensor}. Can't set ${Device::lastReading.name} value")
        }
        device.lastReading = lastReading
        updateDevice(device)
    }

    private suspend fun updateDevice(device: Device) {
        val deviceId = stringPreferencesKey(device.id)
        dataStore.edit { preferences ->
            val deviceJson = Gson().toJson(device)
            preferences[deviceId] = deviceJson
        }
    }

    override suspend fun getDevice(id: String): Device? {
        val key = stringPreferencesKey(id)
        val value = dataStore.data.map { preferences ->
            preferences[key]
        }
        val result = value.firstOrNull()
        if (result != null) {
            return Gson().fromJson(result, Device::class.java)
        }
        return null
    }









}