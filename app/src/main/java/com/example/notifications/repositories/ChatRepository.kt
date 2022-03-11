package com.example.notifications.repositories

import com.example.notifications.data.db.Database
import com.example.notifications.data.db.stocks.Stock
import com.example.notifications.data.db.users.User
import com.example.notifications.data.db.users.UserMessage


class ChatRepository {

    private val userDao = Database.instance.userDao()
    private val stockDao = Database.instance.stockDao()


    suspend fun saveUsers(user: User) {
        userDao.insertUser(listOf(user))
    }

    fun getMessagesWithUser(userId: Long) = userDao.getUsersWithMessages(userId)

    fun getAllMessage() = userDao.getAllUserMessage()

    suspend fun saveUserMessage(message: UserMessage) {
        userDao.insertUserMessage(listOf(message))
    }

    suspend fun saveStock(stock: Stock): List<Long> {
        return stockDao.insertStock(listOf(stock))
    }

}