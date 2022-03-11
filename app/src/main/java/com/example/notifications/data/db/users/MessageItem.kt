package com.example.notifications.data.db.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notifications.data.utils.TimeFormatter
import java.time.Instant

@Composable
fun MessageItem(message: UserMessage) {
    val time = TimeFormatter.format(Instant.ofEpochMilli(message.createdAt))
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = time, fontSize = 10.sp,)
        }
        Column(
            modifier = Modifier
                .background(
                    color = Color.Cyan,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(6.dp)
        ) {
            Text(text = message.userName, fontSize = 12.sp)
            Text(text = message.text)
        }
    }
}