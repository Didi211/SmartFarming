package com.elfak.smartfarming.domain.retrofit.apiWrappers

import com.elfak.smartfarming.data.models.api.ApiResponse
import com.elfak.smartfarming.data.models.api.LoginRequest
import com.elfak.smartfarming.data.models.api.RegisterRequest
import com.elfak.smartfarming.domain.retrofit.apis.AuthApi
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthApiWrapper @Inject constructor(
    private val api: AuthApi
) {
    suspend fun login(email: String, password: String): ApiResponse {
        try {
            return api.login(LoginRequest(email, password))
        }
        catch (ex: ConnectException) {
            throw Exception("Server unavailable. Check the internet connection.")
        }
        catch (ex: Exception) {
            val errorBody = (ex as HttpException).response()!!.errorBody()!!.string()
            return Gson().fromJson(errorBody, ApiResponse::class.java)
        }
    }

    suspend fun register(request: RegisterRequest): ApiResponse {
        try {
            return api.register(request)
        }
        catch (ex: ConnectException) {
            throw Exception("Server unavailable. Check the internet connection.")
        }
        catch (ex: Exception) {
            val errorBody = (ex as HttpException).response()!!.errorBody()!!.string()
            return Gson().fromJson(errorBody, ApiResponse::class.java)
        }
    }
}