package com.example.notifications.compose.stocks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notifications.R
import com.example.notifications.compose.chat.CenterScreenMessage
import com.example.notifications.compose.chat.MyTopAppBar
import com.example.notifications.data.db.stocks.Stock
import com.example.notifications.data.db.stocks.StockItem
import com.example.notifications.ui.theme.NotificationsTheme


@Composable
fun StockScreen(stocksList: List<Stock>) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.promotions),
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
            items(items = stocksList) { stock ->
                StockItem(stock = stock)
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (stocksList.isEmpty()) CenterScreenMessage(message = "No Stocks")

        }
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun StockScreenPreview() {
    NotificationsTheme {
        StockScreen(emptyList())
    }
}





/*
* Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Stock screen")
        Button(onClick = {
            Notifications.showHighPriorityNotification(
                context = context,
                title = "Test title",
                content = "Test content with long text",
                smallIcon = R.drawable.ic_baseline_warning,
                id = Constants.STOCK_HIGH_ID
            )
        }) {
            Text(text = "Take high priority notification")
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            Notifications.showLowPriorityNotification(
                context = context,
                title = "Test low title",
                content = "Test content with long text and low priority",
                smallIcon = R.drawable.info,
                largeIcon = null,
                id = Constants.STOCK_LOW_ID
            )
        }) {
            Text(text = "Take low priority notification")
        }
    }
*
*
*
*
* */