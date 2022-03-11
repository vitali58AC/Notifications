package com.example.notifications.data.db.users

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = UserContract.USER_MESSAGE_TABLE)
data class UserMessage(
    @PrimaryKey(autoGenerate = true)
    val messageId: Long,
    val userOwnerId: Long,
    val userName: String,
    val createdAt: Long,
    val text: String
)
