package com.elfak.smartfarming.data.repositories.interfaces

import com.elfak.smartfarming.data.models.User
import com.elfak.smartfarming.data.models.api.RegisterRequest

interface IRemoteAuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(request: RegisterRequest): User
}