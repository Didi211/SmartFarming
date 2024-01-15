package com.elfak.smartfarming.domain.retrofit.apiWrappers

import com.elfak.smartfarming.data.models.api.ApiResponse
import com.elfak.smartfarming.data.models.api.DeviceRequest
import com.elfak.smartfarming.data.models.api.RuleRequest
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.retrofit.apis.DeviceApi
import com.elfak.smartfarming.domain.utils.ExceptionHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceApiWrapper @Inject constructor(
    private val api: DeviceApi
) {
    // devices
    suspend fun addDevice(device: DeviceRequest, userEmail: String, userId: String): ApiResponse {
        return try {
            api.addDevice(device, userEmail, userId)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }
    suspend fun updateDevice(device: DeviceRequest, userEmail: String): ApiResponse {
        return try {
            api.updateDevice(device.id, device, userEmail)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }
    suspend fun getAllDevices(userId: String, type: DeviceTypes?): ApiResponse {
        return try {
            api.getAllDevices(userId, type?.name)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }
    suspend fun getAvailableDevices(userId: String, type: DeviceTypes): ApiResponse {
        return try {
            api.getAvailableDevices(userId, type.name)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }
    suspend fun getDeviceById(id: String): ApiResponse {
        return try {
            api.getDeviceById(id)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }

    suspend fun removeDevice(id: String, userEmail: String): ApiResponse {
        return try {
            api.removeDevice(id, userEmail)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }

    // rules
    suspend fun getRuleById(id: String): ApiResponse {
        return try {
            api.getRuleById(id)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }
    suspend fun removeRule(id: String, userEmail: String): ApiResponse {
        return try {
            api.removeRule(id, userEmail)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }

    suspend fun getRuleByDeviceId(id: String): ApiResponse {
        return try {
            api.getRuleByDeviceId(id)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }

    suspend fun getAllRules(userId: String): ApiResponse {
        return try {
            api.getAllRules(userId)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }

    suspend fun addRule(rule: RuleRequest, email: String): ApiResponse {
        return try {
            api.addRule(rule, email)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }

    suspend fun updateRule(id: String, rule: RuleRequest, email: String): ApiResponse {
        return try {
            api.updateRule(id, rule, email)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }


}