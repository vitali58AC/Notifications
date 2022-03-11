package com.example.notifications.data.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: RoomDaoDatabase

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            RoomDaoDatabase::class.java,
            RoomDaoDatabase.DB_NAME
        )
            .build()
    }
}