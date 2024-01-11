package com.elfak.smartfarming.data.repositories.interfaces

import com.elfak.smartfarming.data.models.Notification

interface INotificationRepository {
    suspend fun getAll(userId: String): List<Notification>
    suspend fun remove(id: String)
}