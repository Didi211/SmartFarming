package com.elfak.smartfarming.domain.retrofit.apis

import com.elfak.smartfarming.data.models.api.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface DeviceApi {
    @GET("api/devices/user/{userId}")
    suspend fun getAllDevices(@Path("userId") userId: String): ApiResponse

    @DELETE("api/devices/{id}")
    suspend fun removeDevice(@Path("id") id: String, @Header("user-email") userEmail: String): ApiResponse
}