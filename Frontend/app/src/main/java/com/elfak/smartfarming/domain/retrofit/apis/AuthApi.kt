package com.elfak.smartfarming.domain.retrofit.apis

import com.elfak.smartfarming.data.models.api.LoginRequest
import com.elfak.smartfarming.data.models.api.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface AuthApi {
    @GET("api/users/{id}/exists")
    suspend fun isUserExisting(@Path("id") id: String): Call<ApiResponse>

    @POST("api/users/login")
    suspend fun login(@Body loginRequest: LoginRequest): ApiResponse

    @POST("api/users/fetch-mqtt")
    suspend fun fetchMqttToken(@Body email: String): ApiResponse

}