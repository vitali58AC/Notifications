package com.example.notifications.data.db.dao

import androidx.room.*
import com.example.notifications.data.db.users.User
import com.example.notifications.data.db.users.UserContract
import com.example.notifications.data.db.users.UserMessage
import com.example.notifications.data.db.users.UserWithMessages
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: List<User>): List<Long>

    @Transaction
    @Query("SELECT * FROM ${UserContract.USER_TABLE} WHERE ${UserContract.Columns.USER_ID} = :userId")
    fun getUsersWithMessages(userId: Long): Flow<List<UserWithMessages>>

    @Query("SELECT * FROM ${UserContract.USER_MESSAGE_TABLE}")
    fun getAllUserMessage(): Flow<List<UserMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserMessage(messages: List<UserMessage>): List<Long>
}