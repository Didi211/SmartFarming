package com.elfak.smartfarming.data.repositories.interfaces

import com.elfak.smartfarming.data.models.Device

interface ILocalDeviceRepository {
    suspend fun setIsMuted(id: String, isMuted: Boolean)
    suspend fun updateDevicesLocal(devices: List<Device>): List<Device>
    suspend fun updateDeviceLocal(device: Device)
    suspend fun addDevice(device: Device)
    suspend fun getDevice(id: String): Device?
    suspend fun removeDevice(id: String)
    suspend fun setRealTimeData(id: String, lastReading: Double)
}