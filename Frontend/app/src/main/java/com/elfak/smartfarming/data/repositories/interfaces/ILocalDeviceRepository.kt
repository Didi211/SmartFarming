package com.elfak.smartfarming.data.repositories.interfaces

import com.elfak.smartfarming.data.models.Device

interface ILocalDeviceRepository {
    suspend fun setIsMuted(id: String, isMuted: Boolean)
    suspend fun updateDevicesLocal(devices: List<Device>): List<Device>
    suspend fun addDevice(id: String)
    suspend fun removeDevice(id: String)
}