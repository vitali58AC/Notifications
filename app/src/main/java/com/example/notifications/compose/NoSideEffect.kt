package com.example.notifications.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun NoSideEffect(func: () -> Unit) {
    val currentCall by rememberUpdatedState(func)

    LaunchedEffect(true) {
        currentCall()
    }
}