package com.example.notifications

import android.app.Application
import com.example.notifications.data.db.Database
import com.example.notifications.data.notifications.NotificationChannels
import com.example.notifications.data.utils.NetworkMonitoringUtil

class NotificationApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
        Database.init(this)
     // Проверку интернета реализовал иначе, но это тоже рабочий способ
    //   NetworkMonitoringUtil(this).registerNetworkCallbackEvents()
    }
}