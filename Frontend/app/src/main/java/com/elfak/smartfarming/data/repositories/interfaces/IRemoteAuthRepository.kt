package com.elfak.smartfarming.data.repositories.interfaces

import com.elfak.smartfarming.data.models.RegisterUser
import com.elfak.smartfarming.data.models.User

interface IRemoteAuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(user: RegisterUser)
}