package com.elfak.smartfarming.domain.retrofit.apis

import com.elfak.smartfarming.data.models.api.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface NotificationApi {
    @GET("api/notifications/user/{id}")
    suspend fun getAll(@Path("id") userId: String): ApiResponse

    @DELETE("api/notifications/{id}")
    suspend fun remove(@Path("id") id: String): ApiResponse
}