package com.elfak.smartfarming.data.repositories

import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.domain.retrofit.apiWrappers.DeviceApiWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    private val deviceApiWrapper: DeviceApiWrapper

): IDeviceRepository {
    override suspend fun getAllDevices(userId: String): List<Device> {
        val response = deviceApiWrapper.getAllDevices(userId)
        if (response.status != 200) {
            if (response.status == 500) {
                throw Exception(response.message)
            }
            throw Exception("${response.message} - ${response.details}")
        }
        val list = response.details as List<Any?>
        return list.map { item ->
            Device.fromApiResponse(item!!)
        }
    }

    override suspend fun removeDevice(id: String, userEmail: String) {
        val response = deviceApiWrapper.remove(id, userEmail)
        if (response.status != 200) {
            if (response.status == 500) {
                throw Exception(response.message)
            }
            throw Exception("${response.message} - ${response.details}")
        }
    }

}