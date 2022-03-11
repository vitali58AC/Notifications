package com.example.notifications.data.db.users

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = UserContract.USER_TABLE)
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long,
    val userName: String
)
