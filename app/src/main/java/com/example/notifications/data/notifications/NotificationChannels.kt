package com.example.notifications.data.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    val HIGH_PRIORITY_CHANNEL = "high_priority"
    val LOW_PRIORITY_CHANNEL = "low_priority"

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createHighPriorityChannel(context)
            createLowPriorityChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createHighPriorityChannel(context: Context) {
        val name = "Messages"
        val channelDescription = "Urgently message"
        val priority = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(HIGH_PRIORITY_CHANNEL, name, priority).apply {
            description = channelDescription
            //По умолчанию звук есть и точно такой же, но если нужен другой:
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createLowPriorityChannel(context: Context) {
        val name = "Info"
        val channelDescription = "Info messages"
        val priority = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(LOW_PRIORITY_CHANNEL, name, priority).apply {
            description = channelDescription
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

}