package com.example.notifications.data.notifications

import android.util.Log
import com.example.notifications.R
import com.example.notifications.data.db.stocks.Stock
import com.example.notifications.data.db.users.UserMessage
import com.example.notifications.data.utils.LoadImageWithGlide
import com.example.notifications.data.utils.Moshi
import com.example.notifications.data.utils.TimeFormatter
import com.example.notifications.repositories.ChatRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.*
import java.time.Instant

class MessagingService : FirebaseMessagingService() {

    private val chatRepository = ChatRepository()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e("message_service", "New token is $p0")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data["type"] == null) return
        val jsonData = message.data["data"]
        if (jsonData != null) {
            when (message.data["type"]) {
                "message" -> createMessage(jsonData)
                "stock" -> createStock(jsonData)
                else -> Log.e("message_service", "Incorrect remote message type")
            }
        }
    }

    private fun createStock(message: String) {
        val stock = Moshi.convertStockJsonToInstance(message)
        if (stock != null) {
            scope.launch {
                try {
                    chatRepository.saveStock(
                        Stock(
                            stockId = 0,
                            title = stock.title,
                            description = stock.description,
                            imageUrl = stock.imageUrl
                        )
                    )
                    val image = LoadImageWithGlide.loadBitmapWithGlide(
                        this@MessagingService,
                        stock.imageUrl
                    )
                    Notifications.showLowPriorityNotification(
                        context = this@MessagingService,
                        title = stock.title,
                        content = stock.description,
                        smallIcon = R.drawable.info,
                        largeIcon = image,
                        id = stock.stockId.toInt()
                    )
                } catch (t: Throwable) {
                    Log.e("message_service", "error with save stock to db ${t.message}")
                }
            }
        }
    }


    private fun createMessage(message: String) {
        val userMessage = Moshi.convertUserMessageJsonToInstance(message)
        if (userMessage != null) {
            val time = TimeFormatter.format(Instant.ofEpochMilli(userMessage.createdAt))
            scope.launch {
                try {
                    chatRepository.saveUserMessage(
                        UserMessage(
                            messageId = userMessage.messageId,
                            userOwnerId = userMessage.userOwnerId,
                            userName = userMessage.userName,
                            createdAt = Instant.now().toEpochMilli(),
                            text = userMessage.text
                        )
                    )
                } catch (t: Throwable) {
                    Log.e("message_service", "error with save userMessage ${t.message}")
                }
            }
            Notifications.showHighPriorityNotification(
                context = this,
                title = "${userMessage.userName}: $time",
                content = userMessage.text,
                smallIcon = R.drawable.message,
                id = userMessage.userOwnerId.toInt()
            )
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}