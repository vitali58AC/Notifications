package com.example.notifications.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingCardIcon() {
    Icon(
        imageVector = Icons.Filled.ShoppingCart,
        contentDescription = "Go to stock screen",
        modifier = Modifier.size(100.dp),
        tint = MaterialTheme.colors.primaryVariant
    )
}

@Composable
fun ChatIcon() {
    Icon(
        imageVector = Icons.Filled.Chat,
        contentDescription = "Go to chat screen",
        modifier = Modifier.size(100.dp),
        tint = MaterialTheme.colors.primaryVariant
    )
}

@Composable
fun DownloadsIcon() {
    Icon(
        imageVector = Icons.Filled.Download,
        contentDescription = "Go to downloads screen",
        modifier = Modifier.size(100.dp),
        tint = MaterialTheme.colors.primaryVariant
    )
}