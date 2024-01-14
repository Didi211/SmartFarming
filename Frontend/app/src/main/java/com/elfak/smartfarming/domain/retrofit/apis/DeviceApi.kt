package com.elfak.smartfarming.domain.retrofit.apis

import com.elfak.smartfarming.data.models.api.ApiResponse
import com.elfak.smartfarming.data.models.api.DeviceRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface DeviceApi {
    @POST("api/devices")
    suspend fun addDevice(@Body device: DeviceRequest,  @Header("user-email") userEmail: String,  @Header("user-id") userId: String): ApiResponse
    @PUT("api/devices/{id}")
    suspend fun updateDevice(@Path("id")id: String, @Body device: DeviceRequest, @Header("user-email") userEmail: String): ApiResponse
    @GET("api/devices/user/{userId}")
    suspend fun getAllDevices(@Path("userId") userId: String, @Query("type") type: String?): ApiResponse
    @GET("api/devices/{id}")
    suspend fun getDeviceById(@Path("id") id: String): ApiResponse
    @DELETE("api/devices/{id}")
    suspend fun removeDevice(@Path("id") id: String, @Header("user-email") userEmail: String): ApiResponse
    @DELETE("api/devices/{id}/rule")
    suspend fun removeRule(@Path("id") id: String, @Header("user-email") userEmail: String): ApiResponse
    @GET("api/devices/rule/user/{id}")
    suspend fun getAllRules(@Path("id") userId: String): ApiResponse
    @GET("api/devices/{id}/rule")
    suspend fun getRuleByDeviceId(@Path("id") id: String): ApiResponse
}