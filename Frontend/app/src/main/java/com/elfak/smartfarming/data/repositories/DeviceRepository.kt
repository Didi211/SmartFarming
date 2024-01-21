package com.elfak.smartfarming.data.repositories

import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.GraphReading
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.api.DeviceRequest
import com.elfak.smartfarming.data.models.api.GraphDataRequest
import com.elfak.smartfarming.data.models.api.RuleRequest
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.GraphPeriods
import com.elfak.smartfarming.domain.retrofit.apiWrappers.DeviceApiWrapper
import com.elfak.smartfarming.domain.utils.ExceptionHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    private val deviceApiWrapper: DeviceApiWrapper

): IDeviceRepository {
    override suspend fun addDevice(device: DeviceRequest, userEmail: String, userId: String): Device {
        val response = deviceApiWrapper.addDevice(device, userEmail, userId)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        return Device.fromApiResponse(response.details!!)
    }
    override suspend fun updateDevice(device: DeviceRequest, userEmail: String): Device {
        val response = deviceApiWrapper.updateDevice(device, userEmail)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        return Device.fromApiResponse(response.details!!)
    }

    override suspend fun getAllDevices(userId: String, type: DeviceTypes?): List<Device> {
        val response = deviceApiWrapper.getAllDevices(userId, type)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        val list = response.details as List<Any?>
        return list.map { item ->
            Device.fromApiResponse(item!!)
        }
    }

    override suspend fun getAvailableDevices(userId: String, type: DeviceTypes): List<Device> {
        val response = deviceApiWrapper.getAvailableDevices(userId, type)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        val list = response.details as List<Any?>
        return list.map { item ->
            Device.fromApiResponse(item!!)
        }
    }

    override suspend fun getDeviceById(id: String): Device {
        val response = deviceApiWrapper.getDeviceById(id)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        return Device.fromApiResponse(response.details!!)
    }

    override suspend fun getGraphData(
        sensorId: String,
        userId: String,
        period: GraphPeriods,
        graphDataRequest: GraphDataRequest
    ): List<GraphReading> {
        val response = deviceApiWrapper.getGraphData(sensorId, userId, period, graphDataRequest)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        val list = response.details as List<Any?>
        return list.map { item ->
            GraphReading.fromApiResponse(item!!)
        }
    }

    override suspend fun removeDevice(id: String, userEmail: String, userId: String) {
        val response = deviceApiWrapper.removeDevice(id, userEmail, userId)
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

    override suspend fun getRuleByDeviceId(id: String): Rule? {
        val response = deviceApiWrapper.getRuleByDeviceId(id)
        if (response.status == 400) return null
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        return Rule.fromApiResponse(response.details!!)
    }

    override suspend fun getRuleById(id: String): Rule {
        val response = deviceApiWrapper.getRuleById(id)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        return Rule.fromApiResponse(response.details!!)
    }

    override suspend fun addRule(ruleRequest: RuleRequest, email: String): Rule {
        val response = deviceApiWrapper.addRule(ruleRequest, email)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        return Rule.fromApiResponse(response.details!!)
    }

    override suspend fun updateRule(id: String, ruleRequest: RuleRequest, email: String): Rule {
        val response = deviceApiWrapper.updateRule(id, ruleRequest, email)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        return Rule.fromApiResponse(response.details!!)
    }
}