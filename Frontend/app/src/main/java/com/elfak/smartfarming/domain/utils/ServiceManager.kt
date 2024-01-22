@file:Suppress("DEPRECATION")

package com.elfak.smartfarming.domain.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import com.elfak.smartfarming.domain.services.MqttListenerService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor(
    private val context: Context
) {
    fun isServiceRunning(): Boolean {
        return (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == MqttListenerService::class.java.name }
    }
    fun startService() {
        runService(MqttListenerService.ACTION_START)
    }
    fun stopService() {
        runService(MqttListenerService.ACTION_STOP)
    }
    private fun runService(command: String) {
        val intent = Intent(context, MqttListenerService::class.java).apply {
            action = command
        }
        context.startForegroundService(intent)
    }
}