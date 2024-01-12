package com.elfak.smartfarming.domain.retrofit.apiWrappers

import com.elfak.smartfarming.data.models.api.ApiResponse
import com.elfak.smartfarming.domain.retrofit.apis.DeviceApi
import com.elfak.smartfarming.domain.utils.ExceptionHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceApiWrapper @Inject constructor(
    private val api: DeviceApi
) {
    suspend fun getAllDevices(userId: String): ApiResponse {
        return try {
            api.getAllDevices(userId)
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
    suspend fun removeRule(id: String, userEmail: String): ApiResponse {
        return try {
            api.removeRule(id, userEmail)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }
}