package com.elfak.smartfarming.data.repositories.interfaces

import androidx.lifecycle.LiveData
import com.elfak.smartfarming.data.models.Device
import kotlinx.coroutines.flow.Flow

interface ILocalDeviceRepository {
    suspend fun setIsMuted(id: String, isMuted: Boolean)
    suspend fun updateDevicesLocal(devices: List<Device>): Flow<List<Device>>
    suspend fun updateDeviceLocal(device: Device)
    suspend fun addDevice(device: Device)
    suspend fun getDevice(id: String): Device?
    suspend fun removeDevice(id: String)
    suspend fun setRealTimeData(id: String, lastReading: Double)
    suspend fun getDeviceListAsFlow(): Flow<List<Device>>
    suspend fun getDeviceAsFlow(id: String): Flow<Device>
}