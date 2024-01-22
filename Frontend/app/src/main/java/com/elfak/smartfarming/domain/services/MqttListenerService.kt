package com.elfak.smartfarming.domain.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.elfak.smartfarming.MainActivity
import com.elfak.smartfarming.R
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.domain.enums.NavigationConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
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

    // region DI
    @Inject
    lateinit var localDeviceRepository: ILocalDeviceRepository
    //endregion

    //region service functions
    override fun onCreate() {
        super.onCreate()
        createSettingsChannel()
        createAlertChannel()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
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
        val notification = createSettingsNotification("Smart Farming Service started working.")
        startForeground(SERVICE_WORKING_NOTIFICATION_ID, notification)
//        updateNotification(notification, SERVICE_WORKING_NOTIFICATION_ID)
    }



    private fun stopService() {
        stopSelf()
    }

    private suspend fun serviceSetup() {
        // setup mqtt
    }
    // endregion

    // region state methods
    private fun setSensorId(id: String?) {
        serviceState = serviceState.copy(sensorId = id)
    }
    private fun setIsListeningRTData(isListening: Boolean) {
        serviceState = serviceState.copy(isListeningRTData = isListening)
    }
    // endregion

    // region notifications
    private fun createAlertNotification(title:String, contentText: String): Notification {
        val notification = createNotification(ALERT_CHANNEL_ID, title, contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
        return notification.build()
    }
    private fun createSettingsNotification(contentText: String): Notification {
        val notification = createNotification(SERVICE_WORKING_CHANNEL_ID, "SmartFarming background Service", contentText)
            .setContentIntent(createSettingsPendingIntent())
        return notification.build()
    }
    private fun createNotification(channelId: String, title: String, contentText: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.logo_cloud_notification)
    }
    private fun updateNotification(notification: Notification, notificationId: Int) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
    // endregion

    // region channel methods
    private fun createSettingsChannel() {
        val name = "Smart Farming notification channel"
        val description = "Channel for background data fetching over MQTT protocol."
        val importance = NotificationManager.IMPORTANCE_MIN
        val mChannel = NotificationChannel(SERVICE_WORKING_CHANNEL_ID, name, importance)
        mChannel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    private fun createAlertChannel() {
        val name = "Smart Farming Alert notification channel"
        val description = "Channel for emitting alert notifications about devices' working status."
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(SERVICE_WORKING_CHANNEL_ID, name, importance)
        mChannel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }
    // endregion

    // region pending intent methods
    private fun createSettingsPendingIntent() : PendingIntent {
        return createPendingIntent(NavigationConstants.SettingsUri)
    }
    private fun createPendingIntent(deepLink: String): PendingIntent {
        val startActivityIntent = Intent(
            Intent.ACTION_VIEW,
            deepLink.toUri(),
            this,
            MainActivity::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(startActivityIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }
        return resultPendingIntent!!
    }
    // endregion
}