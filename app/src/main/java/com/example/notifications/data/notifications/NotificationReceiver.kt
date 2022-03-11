package com.example.notifications.data.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.notifications.R
import com.example.notifications.data.Constants
import com.example.notifications.data.db.users.UserMessage
import com.example.notifications.repositories.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant

class NotificationReceiver : BroadcastReceiver() {

    private val chatRepository = ChatRepository()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onReceive(context: Context, intent: Intent) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        val id = intent.getIntExtra("id", -100)
        val name = intent.getStringExtra("name") ?: "Name"
        val time = Instant.now().toEpochMilli()
        if (remoteInput != null) {
            val title = remoteInput.getCharSequence(
                Constants.KEY_TEXT_REPLY
            ).toString()
            Log.e("NotificationReceiver", title)
            saveMessage(UserMessage(0L, id.toLong(), name, time, title))
            val notification =
                NotificationCompat.Builder(context, NotificationChannels.LOW_PRIORITY_CHANNEL)
                    .setSmallIcon(R.drawable.message)
                    .setContentTitle("Was replied: $title")
                    .setAutoCancel(true)
                    .build()
            NotificationManagerCompat.from(context)
                .notify(id, notification)
            NotificationManagerCompat.from(context).cancel(id)
        }
    }

    private fun saveMessage(userMessage: UserMessage) {
        scope.launch {
            try {
                chatRepository.saveUserMessage(userMessage)
            } catch (t: Throwable) {
                Log.e("NotificationReceiver", "Error with save message")
            }
        }
    }

}