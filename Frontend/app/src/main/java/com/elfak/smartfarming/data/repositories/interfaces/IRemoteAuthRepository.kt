package com.elfak.smartfarming.data.repositories.interfaces

import com.elfak.smartfarming.data.models.RegisterUser

interface IRemoteAuthRepository {
    suspend fun login(email: String, password: String)
    suspend fun register(user: RegisterUser)
}