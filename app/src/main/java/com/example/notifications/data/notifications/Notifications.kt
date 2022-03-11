package com.example.notifications.data.notifications

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.net.toUri
import com.example.notifications.MainActivity
import com.example.notifications.R
import com.example.notifications.data.Constants.KEY_TEXT_REPLY
import com.example.notifications.data.Screens
import kotlinx.coroutines.*

object Notifications {

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun showHighPriorityNotification(
        context: Context,
        title: String,
        content: String,
        @DrawableRes smallIcon: Int,
        id: Int
    ) {
        val taskDetailIntent = Intent(
            Intent.ACTION_VIEW,
            "https://example.com/${Screens.Chat.name}".toUri(),
            context,
            MainActivity::class.java
        )

        val pending: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(taskDetailIntent)
            getPendingIntent(124, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val replyLabel: String = context.resources.getString(R.string.reply_label)
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
            .setLabel(replyLabel)
            .build()

        val resultIntent = Intent(context, NotificationReceiver::class.java)
            .putExtra("id", id)
            .putExtra("name", title.split(":")[0])

        val replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            123,
            resultIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
        val action: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                R.drawable.reply,
                "Reply", replyPendingIntent
            )
                .addRemoteInput(remoteInput)
                .build()

        val notification =
            NotificationCompat.Builder(context, NotificationChannels.HIGH_PRIORITY_CHANNEL)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .addAction(action)
                .build()
        NotificationManagerCompat.from(context)
            .notify(id, notification)
    }

    fun showLowPriorityNotification(
        context: Context,
        title: String,
        content: String,
        @DrawableRes smallIcon: Int,
        largeIcon: Bitmap?,
        id: Int
    ) {
        val taskDetailIntent = Intent(
            Intent.ACTION_VIEW,
            "https://example.com/${Screens.Stocks.name}".toUri(),
            context,
            MainActivity::class.java
        )

        val pending: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(taskDetailIntent)
            getPendingIntent(123, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification =
            NotificationCompat.Builder(context, NotificationChannels.LOW_PRIORITY_CHANNEL)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(smallIcon)
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .build()
        NotificationManagerCompat.from(context)
            .notify(id, notification)
    }

    fun showDownloadingProgressNotification(context: Context, progress: Long, fileSize: Long) {
        val notificationBuilder = NotificationCompat.Builder(
            context,
            NotificationChannels.LOW_PRIORITY_CHANNEL
        )
            .setContentTitle("Update downloading")
            .setContentText("Downloading in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.downloading)

        val notification = notificationBuilder
            .setProgress(fileSize.toInt(), progress.toInt(), false)
            .build()

        NotificationManagerCompat.from(context).notify(fileSize.toInt(), notification)

        val finalNotification = notificationBuilder
            .setContentText("Download is completed")
            .setProgress(0, 0, false)
            .build()

        scope.launch {
            if (progress >= fileSize) {
                NotificationManagerCompat.from(context).notify(fileSize.toInt(), finalNotification)
            delay(1000)
                NotificationManagerCompat.from(context).cancel(fileSize.toInt())
            }
        }
    }


}

