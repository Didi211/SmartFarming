package com.elfak.smartfarming.data.repositories.interfaces
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.api.DeviceRequest
import com.elfak.smartfarming.domain.enums.DeviceTypes


interface IDeviceRepository {
    suspend fun addDevice(device: DeviceRequest, userEmail: String, userId: String): Device
    suspend fun updateDevice(device: DeviceRequest, userEmail: String): Device
    suspend fun getAllDevices(userId: String, type: DeviceTypes? = null): List<Device>
    suspend fun getDeviceById(id: String): Device
    suspend fun removeDevice(id: String, userEmail: String)
    suspend fun removeRule(id: String, userEmail: String)
    suspend fun getAllRules(userId: String): List<Rule>
    suspend fun getRuleByDeviceId(id: String): Rule?

}