package com.example.notifications

import android.app.NotificationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.app.RemoteInput
import androidx.navigation.compose.rememberNavController
import com.example.notifications.compose.SystemBroadcastReceiver
import com.example.notifications.compose.navigation.Navigation
import com.example.notifications.data.Constants.KEY_TEXT_REPLY
import com.example.notifications.data.notifications.Notifications
import com.example.notifications.data.utils.ConnectionLiveData
import com.example.notifications.ui.theme.NotificationsTheme
import com.example.notifications.view_models.ChatViewModel
import com.example.notifications.view_models.DownloadViewModel
import com.example.notifications.view_models.StockViewModel
import kotlinx.coroutines.cancelChildren

class MainActivity : ComponentActivity() {

    private val stockViewModel: StockViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    private val downloadViewModel: DownloadViewModel by viewModels()

    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)

        setContent {
            val isNetworkAvailable = connectionLiveData.observeAsState(false).value
            val navController = rememberNavController()
            NotificationsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SystemBroadcastReceiver(systemAction = "example.action") { intent ->
                        Log.e("main_activity", "context receiver work")
                         val input = RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)
                        Log.e("main_activity", "context receiver work input $input")
                    }
                    Navigation(
                        context = this,
                        navController = navController,
                        stockViewModel = stockViewModel,
                        chatViewModel = chatViewModel,
                        downloadViewModel = downloadViewModel,
                        isNetworkAvailable = isNetworkAvailable
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }

    override fun onDestroy() {
        Notifications.scope.coroutineContext.cancelChildren()
        super.onDestroy()
    }
}