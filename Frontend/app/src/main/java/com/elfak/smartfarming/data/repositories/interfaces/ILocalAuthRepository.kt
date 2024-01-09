package com.elfak.smartfarming.data.repositories.interfaces

import com.elfak.smartfarming.data.models.UserCredentials

interface ILocalAuthRepository {
    suspend fun getCredentials(): UserCredentials
    suspend fun setCredentials(credentials: UserCredentials)
}