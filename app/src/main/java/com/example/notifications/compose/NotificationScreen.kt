package com.example.notifications.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notifications.R

@Composable
fun NotificationsScreen(
    onStockClick: () -> Unit,
    onChatClick: () -> Unit,
    onDownloadClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        IconWithButton(
            icon = { ShoppingCardIcon() },
            buttonText = stringResource(R.string.stocks),
            onClick = onStockClick
        )
        IconWithButton(
            icon = { ChatIcon() },
            buttonText = stringResource(R.string.chat),
            onClick = onChatClick
        )
        IconWithButton(
            icon = { DownloadsIcon() },
            buttonText = stringResource(R.string.downloads),
            onClick = onDownloadClick
        )
    }
}


@Composable
fun IconWithButton(icon: @Composable () -> Unit, buttonText: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon()
        Button(onClick = { onClick() }) {
            Text(text = buttonText, color = MaterialTheme.colors.background)
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    NotificationsScreen(
        onStockClick = { /*TODO*/ },
        onChatClick = { /*TODO*/ },
        onDownloadClick = { }
    )
}