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
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import co.yml.charts.common.extensions.roundTwoDecimal
import com.elfak.smartfarming.MainActivity
import com.elfak.smartfarming.R
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.User
import com.elfak.smartfarming.data.models.mqtt.AlertMessage
import com.elfak.smartfarming.data.models.mqtt.RealTimeData
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.data.repositories.interfaces.ISettingsRepository
import com.elfak.smartfarming.domain.enums.DeviceStatus
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.NavigationConstants
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.domain.enums.toDeviceStatus
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject


@AndroidEntryPoint
class MqttListenerService @Inject constructor(): Service(), MqttCallbackExtended {

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
    @Inject
    lateinit var settingsRepository: ISettingsRepository
    @Inject
    lateinit var mqttClient: MqttAndroidClient
    @Inject
    lateinit var localAuthRepository: ILocalAuthRepository
    //endregion

    //region service functions
    override fun onCreate() {
        super.onCreate()
        serviceScope.launch {
            val user = localAuthRepository.getCredentials()
            setUser(user)
        }
        createSettingsChannel()
        createAlertChannel()
        setSettings()
        Log.d("MQTT Service - Create", "Service has been created.")

    }



    override fun onDestroy() {
        Log.d("MQTT Service - Destroy", "Service will be destroyed.")

        super.onDestroy()
        serviceScope.cancel()
        mqttClient.disconnect()
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
        val notification = createSettingsNotification("Service started working.")
        startForeground(SERVICE_WORKING_NOTIFICATION_ID, notification)
        Log.d("MQTT Service - Start", "Service has started.")

    }

    private fun stopService() {
        stopSelf()
        Log.d("MQTT Service - Stop", "Service has stopped.")
    }

    private fun serviceSetup() {
        // setup mqtt
        mqttClient.setCallback(this)
        mqttClient.connect()
        Log.d("MQTT Service - Startup", "Service setup done")
    }
    // endregion

    // region state methods
    private fun setIsSubscribedToAlerts(isSubscribed: Boolean) {
        serviceState = serviceState.copy(isSubscribedToAlerts = isSubscribed)

    }
    private fun setIsSubscribedToRtData(isSubscribed: Boolean) {
        serviceState = serviceState.copy(isSubscribedToRtData = isSubscribed)
    }
    private fun setUser(user: User) {
        serviceState = serviceState.copy(user = user)
    }
    private fun setIsListeningRTData(isListening: Boolean) {
        serviceState = serviceState.copy(isRealTimeDataEnabled = isListening)
    }
    private fun setIsNotificationSoundEnabled(enabled: Boolean) {
        serviceState = serviceState.copy(isNotificationSoundEnabled = enabled)
    }
    private fun setSettings() {
        serviceScope.launch {
            val rtSettingFlow = settingsRepository.getRealTimeSetting()
            rtSettingFlow.collect {
                setIsListeningRTData(it ?: false)
                Log.d("MQTT Service - Settings", "Real Time Setting changed to $it")
                if (!mqttClient.isConnected)
                    return@collect
                if (it != null) {
                    if (serviceState.isSubscribedToRtData != it) {
                        val topic = "rt-data/${serviceState.user.mqttToken}"
                        if (it) subscribeToTopic(topic) else unsubscribeFromTopic(topic)
                        setIsSubscribedToRtData(it)
                    }
                }
            }
        }
        serviceScope.launch {
            val soundNotifFlow = settingsRepository.getSoundSetting()
            soundNotifFlow.collect {
                setIsNotificationSoundEnabled(it?: false)
                Log.d("MQTT Service - Settings", "Sound Notification Setting changed to $it")
            }
        }
    }
    // endregion

    // region notifications
    private fun createAlertNotification(title:String, contentText: String, deviceId: String): Notification {
        val notification = createNotification(ALERT_CHANNEL_ID, title, contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setContentIntent(createAlertPendingIntent(deviceId))
            .setAutoCancel(true)
        return notification.build()
    }



    private fun createSettingsNotification(contentText: String): Notification {
        val notification = createNotification(SERVICE_WORKING_CHANNEL_ID, "SmartFarming background service", contentText)
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
        val description = "Channel for emitting alert notifications about device's working status."
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(ALERT_CHANNEL_ID, name, importance)
        mChannel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }
    // endregion

    // region pending intent methods
    private fun createSettingsPendingIntent() : PendingIntent {
        return createPendingIntent(NavigationConstants.SettingsUri)
    }
    private fun createAlertPendingIntent(deviceId: String): PendingIntent? {
        return createPendingIntent("${NavigationConstants.DeviceDetailsUri}/${deviceId}/${ScreenState.View.name}")
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

    // region Mqtt methods
    override fun connectionLost(cause: Throwable?) {

    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        Log.d("MQTT Service - New message", message.toString())
        if (topic != null) {
            when (topic.split('/')[0].lowercase()) {
                "rt-data" -> handleRealTimeDataMessages(message.toString())
                "alerts" -> handleAlertsMessages(message.toString())
                else -> { }
            }
        }
    }

    private fun handleAlertsMessages(message: String?) {
        if (message != null) {
            val alert =  Gson().fromJson(message, AlertMessage::class.java)
            serviceScope.launch {
                var device = localDeviceRepository.getDevice(alert.metadata.deviceId)
                if (device == null) {
                    val newDevice = Device(id =  alert.metadata.deviceId, status = alert.metadata.status.toDeviceStatus())
                   localDeviceRepository.addDevice(newDevice)
                    device = newDevice
                }
                if (shouldNotifyUser(device, alert.metadata.status.toDeviceStatus())) {
                    val notification = createAlertNotification(title = "Device status alert", contentText = alert.message, alert.metadata.deviceId)
                    updateNotification(notification, alert.metadata.deviceId.hashCode())
//                    updateNotification(notification, ALERT_CHANNEL_NOTIFICATION_ID)
                }
                device.status = alert.metadata.status.toDeviceStatus()
                localDeviceRepository.updateDeviceLocal(device)
            }
        }
    }

    private fun shouldNotifyUser(device: Device, alertStatus: DeviceStatus): Boolean {
        return serviceState.isNotificationSoundEnabled
                && !device.isMuted
                && alertStatus != device.status
    }

    private fun handleRealTimeDataMessages(message: String?) {
        if (message != null) {
            val realTimeData = Gson().fromJson(message, RealTimeData::class.java)
            serviceScope.launch {
                val device = localDeviceRepository.getDevice(realTimeData.sensorId)
                if (device == null) {
                    val newDevice = Device(
                        id = realTimeData.sensorId,
                        type = DeviceTypes.Sensor,
                    )
                    localDeviceRepository.addDevice(newDevice)
                }
                localDeviceRepository.setRealTimeData(realTimeData.sensorId, realTimeData.reading.roundTwoDecimal())
            }
        }
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {

    }

    override fun connectComplete(reconnect: Boolean, serverURI: String?) {
        Log.d("MQTT Service - Connection", "Connected to MQTT")
        val user = runBlocking {localAuthRepository.getCredentials() }
        if (!serviceState.isSubscribedToAlerts) {
            subscribeToTopic("alerts/${user.mqttToken}")
            setIsSubscribedToAlerts(true)
        }
        if (serviceState.isRealTimeDataEnabled && !serviceState.isSubscribedToRtData) {
            subscribeToTopic("rt-data/${user.mqttToken}")
            setIsSubscribedToRtData(true)
        }
    }

    private fun subscribeToTopic(topic: String) {
        mqttClient.subscribe(topic, 0)
        Log.d("MQTT Service - Subscription", "Subscribed to topic [${topic}]")
    }
    private fun unsubscribeFromTopic(topic: String) {
        mqttClient.unsubscribe(topic)
        Log.d("MQTT Service - Unsubscription", "Unsubscribed from topic [${topic}]")

    }
    // endregion
}