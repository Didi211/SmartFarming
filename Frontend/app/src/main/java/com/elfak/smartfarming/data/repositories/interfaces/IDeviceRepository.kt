package com.elfak.smartfarming.data.repositories.interfaces
import com.elfak.smartfarming.data.models.Device


interface IDeviceRepository {
    suspend fun getAllDevices(userId: String): List<Device>
    suspend fun removeDevice(id: String, userEmail: String)
}