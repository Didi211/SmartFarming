package com.elfak.smartfarming.data.repositories

import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.domain.retrofit.apiWrappers.DeviceApiWrapper
import com.elfak.smartfarming.domain.utils.ExceptionHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    private val deviceApiWrapper: DeviceApiWrapper

): IDeviceRepository {
    override suspend fun getAllDevices(userId: String): List<Device> {
        val response = deviceApiWrapper.getAllDevices(userId)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        val list = response.details as List<Any?>
        return list.map { item ->
            Device.fromApiResponse(item!!)
        }
    }

    override suspend fun removeDevice(id: String, userEmail: String) {
        val response = deviceApiWrapper.removeDevice(id, userEmail)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
    }

    override suspend fun removeRule(id: String, userEmail: String) {
        val response = deviceApiWrapper.removeRule(id, userEmail)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
    }

    override suspend fun getAllRules(userId: String): List<Rule> {
        val response = deviceApiWrapper.getAllRules(userId)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        val list = response.details as List<Any?>
        return list.map { item ->
            Rule.fromApiResponse(item!!)
        }
    }

}