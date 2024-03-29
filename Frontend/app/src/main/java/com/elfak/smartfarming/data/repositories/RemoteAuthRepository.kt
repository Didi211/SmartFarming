package com.elfak.smartfarming.data.repositories

import com.elfak.smartfarming.data.models.User
import com.elfak.smartfarming.data.models.api.RegisterRequest
import com.elfak.smartfarming.data.repositories.interfaces.IRemoteAuthRepository
import com.elfak.smartfarming.domain.retrofit.apiWrappers.AuthApiWrapper
import com.elfak.smartfarming.domain.utils.ExceptionHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteAuthRepository @Inject constructor(
    private val authApiWrapper: AuthApiWrapper
): IRemoteAuthRepository {
    override suspend fun login(email: String, password: String): User {
        val response = authApiWrapper.login(email, password)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        return User.fromApiResponse(response.details!!)
    }

    override suspend fun register(request: RegisterRequest): User {
        val response = authApiWrapper.register(request)
        if (response.status != 200) {
            ExceptionHandler.throwApiResponseException(response)
        }
        return User.fromApiResponse(response.details!!)
    }

}