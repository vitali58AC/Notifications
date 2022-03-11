package com.example.notifications.data.db.users

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithMessages(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val messages: List<UserMessage>
)
