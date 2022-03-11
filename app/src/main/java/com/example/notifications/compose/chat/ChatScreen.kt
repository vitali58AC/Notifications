package com.example.notifications.compose.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notifications.R
import com.example.notifications.data.db.users.MessageItem
import com.example.notifications.data.db.users.UserMessage
import com.example.notifications.ui.theme.NotificationsTheme

@Composable
fun ChatScreen(messages: List<UserMessage>) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.working_chat),
                icon = { },
                onIconClick = { }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(items = messages) { message ->
                MessageItem(message = message)
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (messages.isEmpty()) CenterScreenMessage(message = "No messages")

        }
    }
}

@Composable
fun ColumnScope.CenterScreenMessage(message: String) {
    Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material.Text(
            text = message,
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun MyTopAppBar(title: String, icon: @Composable () -> Unit, onIconClick: () -> Unit = {}) {
    TopAppBar(
        title = {
            androidx.compose.material.Text(
                text = title,
                color = androidx.compose.material.MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Light
            )
        },
        backgroundColor = androidx.compose.material.MaterialTheme.colors.primary,
        actions = {
            IconButton(onClick = { onIconClick() }) {
                icon()
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun ChatScreenPreview() {
    NotificationsTheme {
        ChatScreen(messages = listOf(
            UserMessage(0L, -1L, "Vitali", 123123131L, " Helloy!"),
            UserMessage(0L, -1L, "Admin", 123123132L, " Helloy Vitali!"),
            UserMessage(0L, -1L, "Vitali", 123123133L, " Bye!"),
            UserMessage(0L, -1L, "Admin", 123123134L, " Bye Vitali!!"),
        ))
    }
}