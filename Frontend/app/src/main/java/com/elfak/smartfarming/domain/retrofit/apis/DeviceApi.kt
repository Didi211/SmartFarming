package com.elfak.smartfarming.domain.retrofit.apis

import com.elfak.smartfarming.data.models.api.ApiResponse
import com.elfak.smartfarming.data.models.api.DeviceRequest
import com.elfak.smartfarming.data.models.api.GraphDataRequest
import com.elfak.smartfarming.data.models.api.RuleRequest
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
    // devices
    @POST("api/devices")
    suspend fun addDevice(@Body device: DeviceRequest,  @Header("user-email") userEmail: String,  @Header("user-id") userId: String): ApiResponse
    @PUT("api/devices/{id}")
    suspend fun updateDevice(@Path("id")id: String, @Body device: DeviceRequest, @Header("user-email") userEmail: String): ApiResponse
    @GET("api/devices/user/{userId}")
    suspend fun getAllDevices(@Path("userId") userId: String, @Query("type") type: String?): ApiResponse
    @GET("api/devices/user/{userId}/type/{type}/available")
    suspend fun getAvailableDevices(@Path("userId")userId: String, @Path("type") type: String): ApiResponse
    @POST("api/sensor-data/{id}")
    suspend fun getGraphData(@Path("id") sensorId: String, @Header("user-id") useId: String, @Body graphRequest: GraphDataRequest, @Query("period") period: String): ApiResponse

    @GET("api/devices/{id}")
    suspend fun getDeviceById(@Path("id") id: String): ApiResponse
    @DELETE("api/devices/{id}")
    suspend fun removeDevice(@Path("id") id: String, @Header("user-email") userEmail: String, @Header("user-id") userId: String): ApiResponse

    // rules
    @GET("api/devices/rule/{id}")
    suspend fun getRuleById(@Path("id") id: String): ApiResponse
    @DELETE("api/devices/rule/{id}")
    suspend fun removeRule(@Path("id") id: String, @Header("user-email") userEmail: String): ApiResponse
    @GET("api/devices/rule/user/{id}")
    suspend fun getAllRules(@Path("id") userId: String): ApiResponse
    @GET("api/devices/{id}/rule")
    suspend fun getRuleByDeviceId(@Path("id") id: String): ApiResponse
    @PUT("api/devices/rule/{id}")
    suspend fun updateRule(@Path("id") id: String, @Body rule: RuleRequest, @Header("user-email") email: String): ApiResponse
    @POST("api/devices/rule")
    suspend fun addRule(@Body rule: RuleRequest, @Header("user-email") email: String): ApiResponse
}