package com.elfak.smartfarming.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

val Context.deviceDataStore: DataStore<Preferences> by preferencesDataStore(name = "devices")

@Singleton
class LocalDeviceRepository @Inject constructor(
    context: Context
) : ILocalDeviceRepository {

    private val dataStore = context.deviceDataStore

    override suspend fun setIsMuted(id: String, isMuted: Boolean) {
        val device = getDevice(id)
            ?: throw Exception("Device with ID [${id}] not found in local storage.")
        device.isMuted = isMuted
        updateDeviceLocal(device)
    }

    override suspend fun updateDevicesLocal(devices: List<Device>): Flow<List<Device>> {
        val localDevicesMap = dataStore.data.first().asMap()
        val ids = localDevicesMap.map { it.key.toString() }

        // adding new devices
        devices.forEach { device ->
            if (!ids.contains(device.id)) {
                addDevice(device)
            }
            else {
                val localDevice = getDevice(device.id)
                updateDeviceLocal(localDevice!!.copy(
                    name = device.name,
                    status = device.status,
                    unit = device.unit,
                    state = device.state,
                ))
            }
        }

        // removing invalid devices
        ids.forEach { id ->
            if (devices.none { device -> device.id == id }) {
                removeDevice(id)
            }
        }
        return getDeviceListAsFlow()
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
        val device = getDevice(id)
            ?: throw Exception("Device with ID [${id}] not found in local storage.")
        if (device.type != DeviceTypes.Sensor) {
            throw Exception("Device ${device.name} is not ${DeviceTypes.Sensor}. Can't set ${Device::lastReading.name} value")
        }
        device.lastReading = lastReading
        device.lastReadingTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).toString()
        updateDeviceLocal(device)
    }

    override suspend fun updateDeviceLocal(device: Device) {
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

    override suspend fun getDeviceAsFlow(id: String): Flow<Device> {
        val key = stringPreferencesKey(id)
        return dataStore.data.map { preferences ->
            val device = Gson().fromJson(preferences[key], Device::class.java)
            device
        }
//        value.onEach {
//            trySend(Gson().fromJson(it, Device::class.java))
//        }
    }
    override suspend fun getDeviceListAsFlow(): Flow<List<Device>> {
        return dataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
            preferences.asMap().map { device ->
                Gson().fromJson(device.value.toString(), Device::class.java)
            }
        }
    }









}