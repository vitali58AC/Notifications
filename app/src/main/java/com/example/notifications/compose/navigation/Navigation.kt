package com.example.notifications.compose.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.notifications.compose.NoSideEffect
import com.example.notifications.compose.NotificationsScreen
import com.example.notifications.compose.chat.ChatScreen
import com.example.notifications.compose.downloads.DownloadsScreen
import com.example.notifications.compose.stocks.StockScreen
import com.example.notifications.data.Screens
import com.example.notifications.data.notifications.Notifications
import com.example.notifications.view_models.ChatViewModel
import com.example.notifications.view_models.DownloadViewModel
import com.example.notifications.view_models.StockViewModel

@Composable
fun Navigation(
    context: Context,
    navController: NavHostController,
    stockViewModel: StockViewModel,
    chatViewModel: ChatViewModel,
    downloadViewModel: DownloadViewModel,
    isNetworkAvailable: Boolean
) {
    val exampleUri = "https://example.com"
    NavHost(navController = navController, startDestination = Screens.Host.name) {
        composable(Screens.Host.name) {
            NotificationsScreen(
                onStockClick = { navController.navigate(Screens.Stocks.name) },
                onChatClick = { navController.navigate(Screens.Chat.name) },
                onDownloadClick = { navController.navigate(Screens.Downloads.name) }
            )
            if (stockViewModel.firstLaunchState) {
                NoSideEffect(stockViewModel::checkToken)
            }
        }
        composable(
            route = Screens.Stocks.name,
            deepLinks = listOf(navDeepLink { uriPattern = "$exampleUri/${Screens.Stocks.name}" })
        ) {
            NoSideEffect(stockViewModel::getMessagesList)
            StockScreen(stockViewModel.stockListState)
        }
        composable(
            route = Screens.Chat.name,
            deepLinks = listOf(navDeepLink { uriPattern = "$exampleUri/${Screens.Chat.name}" })
        ) {
            NoSideEffect(chatViewModel::getMessagesList)
            NoSideEffect(chatViewModel::getAllMessage)
            ChatScreen(messages = chatViewModel.allMessagesList)
        }
        composable(Screens.Downloads.name) {
            DownloadsScreen(
                isNetworkAvailable = isNetworkAvailable,
                onDownloadClick = { url ->
                    downloadViewModel.downloadVideo(url) { progress, fileSize ->
                        Notifications.showDownloadingProgressNotification(
                            context = context,
                            progress = progress,
                            fileSize = fileSize
                        )
                    }
                }
            )
        }
    }
}



