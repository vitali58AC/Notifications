package com.example.notifications.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifications.data.db.users.User
import com.example.notifications.data.db.users.UserMessage
import com.example.notifications.repositories.ChatRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val repository = ChatRepository()

    val messagesList = mutableStateListOf<UserMessage>()
    val allMessagesList = mutableStateListOf<UserMessage>()

    init {
        viewModelScope.launch {
            try {
                repository.saveUsers(User(-1L,"Admin"))
            } catch (t: Throwable) {
                Log.e("chat_view_model", "error with save default user ${t.message}")
            }
        }
    }

    fun getMessagesList(userId: Long = -1L) {
        viewModelScope.launch {
            try {
                repository.getMessagesWithUser(userId).collect { relation ->
                    messagesList.clear()
                    messagesList.addAll(relation[0].messages)
                }
            } catch (t: Throwable) {
                Log.e("chat_view_model", "error with get messages list ${t.message}")
            }
        }
    }

    fun getAllMessage() {
        viewModelScope.launch {
            try {
                repository.getAllMessage().collect { userMessage ->
                    allMessagesList.clear()
                    allMessagesList.addAll(userMessage)
                }
            } catch (t: Throwable) {
                Log.e("chat_view_model", "error with get all messages ${t.message}")
            }
        }
    }

}