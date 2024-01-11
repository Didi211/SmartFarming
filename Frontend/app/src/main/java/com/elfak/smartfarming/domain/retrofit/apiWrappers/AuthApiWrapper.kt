package com.elfak.smartfarming.domain.retrofit.apiWrappers

import com.elfak.smartfarming.data.models.api.ApiResponse
import com.elfak.smartfarming.data.models.api.LoginRequest
import com.elfak.smartfarming.data.models.api.RegisterRequest
import com.elfak.smartfarming.domain.retrofit.apis.AuthApi
import com.elfak.smartfarming.domain.utils.ExceptionHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthApiWrapper @Inject constructor(
    private val api: AuthApi
) {
    suspend fun login(email: String, password: String): ApiResponse {
        return try {
            api.login(LoginRequest(email, password))
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }

    suspend fun register(request: RegisterRequest): ApiResponse {
        return try {
            api.register(request)
        } catch (ex: Exception) {
            ExceptionHandler.handleApiCallException(ex)
        }
    }
}