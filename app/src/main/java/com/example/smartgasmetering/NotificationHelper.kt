package com.example.smartgasmetering

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHelper(private val context: Context) {
    fun sendLowBatteryNotification(serialNumber: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "low_battery_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {// TIRAMISU = API 33
            val channel = NotificationChannel(
                channelId,
                "Low Battery Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.battery_alert)
            .setContentTitle("Low Battery Warning")
            .setContentText("Meter $serialNumber has low battery!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(serialNumber.hashCode(), notification)
    }
}