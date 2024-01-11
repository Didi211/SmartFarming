package com.elfak.smartfarming.data.repositories

import com.elfak.smartfarming.data.models.Notification
import com.elfak.smartfarming.data.repositories.interfaces.INotificationRepository
import com.elfak.smartfarming.domain.retrofit.apiWrappers.NotificationApiWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationApiWrapper: NotificationApiWrapper

): INotificationRepository {
    override suspend fun getAll(userId: String): List<Notification> {
        val response = notificationApiWrapper.getAll(userId)
        if (response.status != 200) {
            if (response.status == 500) {
                throw Exception(response.message)
            }
            throw Exception("${response.message} - ${response.details}")
        }
        val list = response.details as List<Any?>
        return list.map { item ->
            Notification.fromApiResponse(item!!)
        }
    }

    override suspend fun remove(id: String) {
        val response = notificationApiWrapper.remove(id)
        if (response.status != 200) {
            if (response.status == 500) {
                throw Exception(response.message)
            }
            throw Exception("${response.message} - ${response.details}")
        }
    }
}