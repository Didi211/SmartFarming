package com.elfak.smartfarming.domain.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MqttListenerService @Inject constructor(): Service() {

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        private const val SERVICE_WORKING_CHANNEL_ID = "MqttListenerChannel"
        private const val SERVICE_WORKING_NOTIFICATION_ID = 1
        private const val ALERT_CHANNEL_ID = "AlertNotificationChannel"
        private const val ALERT_CHANNEL_NOTIFICATION_ID = 2
    }
    private  var serviceState by mutableStateOf(MqttListenerState())

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun onBind(intent: Intent?): IBinder? {
        return null  // We don't provide binding, so return null
    }

    val name: String
        get() = this::class.simpleName.toString()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            when (intent?.action) {
                ACTION_START -> { startService() }
                ACTION_STOP -> { stopService() }
            }
        }
        return START_STICKY
    }

    @SuppressLint("ForegroundServiceType")
    private fun startService() {
        serviceScope.launch {
            serviceSetup()
        }
        val notification = createSettingsNotification()
        startForeground(SERVICE_WORKING_NOTIFICATION_ID, notification)
    }



    private fun stopService() {
        stopSelf()
    }

    private fun serviceSetup() {

    }

    // region notifications
    private fun createAlertNotification(title:String, contentText: String, tourId: String): Notification {
        val notification = createNotification(TOUR_CHANNEL_ID, title, contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setContentIntent(createTourPendingIntent(tourId))
        return notification.build()
    }
    private fun createSettingsNotification(contentText: String): Notification {
        val notification = createNotification(SERVICE_WORKING_CHANNEL_ID, "Location Tracking Service", contentText)
        return notification.build()
    }
    private fun createNotification(channelId: String, title: String, contentText: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_stat_onesignal_default)
    }
    private fun updateNotification(notification: Notification, notificationId: Int) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
    // endregion
}