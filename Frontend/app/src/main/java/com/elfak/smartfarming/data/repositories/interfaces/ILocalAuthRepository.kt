package com.elfak.smartfarming.data.repositories.interfaces

import com.elfak.smartfarming.data.models.User

interface ILocalAuthRepository {
    suspend fun getCredentials(): User
    suspend fun setCredentials(credentials: User)
    suspend fun removeCredentials()
}