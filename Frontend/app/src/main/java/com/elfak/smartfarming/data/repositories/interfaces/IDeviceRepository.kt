package com.elfak.smartfarming.data.repositories.interfaces
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule


interface IDeviceRepository {
    suspend fun getAllDevices(userId: String): List<Device>
    suspend fun removeDevice(id: String, userEmail: String)
    suspend fun removeRule(id: String, userEmail: String)
    suspend fun getAllRules(userId: String): List<Rule>

}