package com.example.notifications.data.db.stocks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun StockItem(stock: Stock) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), shape = RoundedCornerShape(8.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.padding(8.dp)) {
                Text(text = stock.title, fontWeight = FontWeight.Black)
                Text(text = stock.description)
            }
            Image(
                painter = rememberImagePainter(stock.imageUrl),
                contentDescription = "Promotion logo",
                modifier = Modifier.size(64.dp)
            )
        }
    }
}