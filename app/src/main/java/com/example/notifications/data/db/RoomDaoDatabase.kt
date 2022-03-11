package com.example.notifications.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notifications.data.db.dao.StockDao
import com.example.notifications.data.db.dao.UserDao
import com.example.notifications.data.db.stocks.Stock
import com.example.notifications.data.db.users.User
import com.example.notifications.data.db.users.UserMessage

@Database(
    entities = [
        User::class,
        UserMessage::class,
        Stock::class
    ],
    version = RoomDaoDatabase.DB_VERSION
)
abstract class RoomDaoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun stockDao(): StockDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "room-dao-data"
    }
}
