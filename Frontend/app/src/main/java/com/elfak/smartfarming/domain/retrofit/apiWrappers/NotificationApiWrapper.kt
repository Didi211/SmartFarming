package com.elfak.smartfarming.domain.retrofit.apiWrappers

import com.elfak.smartfarming.data.models.api.ApiResponse
import com.elfak.smartfarming.domain.retrofit.apis.NotificationApi
import com.elfak.smartfarming.domain.utils.ExceptionHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class NotificationApiWrapper @Inject constructor(
    private val api: NotificationApi
) {
    suspend fun getAll(userId: String): ApiResponse {
        return try {
            api.getAll(userId)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }

    suspend fun remove(id: String): ApiResponse {
        return try {
            api.remove(id)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }


}