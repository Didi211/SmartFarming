package com.elfak.smartfarming.data.repositories

import com.elfak.smartfarming.data.models.RegisterUser
import com.elfak.smartfarming.data.repositories.interfaces.IRemoteAuthRepository

class RemoteAuthRepository: IRemoteAuthRepository {
    override suspend fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun register(user: RegisterUser) {
        TODO("Not yet implemented")
    }
}