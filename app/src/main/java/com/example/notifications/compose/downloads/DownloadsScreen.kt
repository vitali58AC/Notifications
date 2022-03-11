package com.example.notifications.compose.downloads

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.notifications.MainActivity
import com.example.notifications.compose.SystemBroadcastReceiver
import com.example.notifications.data.utils.PERMISSION
import com.example.notifications.data.utils.hasPermissions

@Composable
fun DownloadsScreen(isNetworkAvailable: Boolean, onDownloadClick: (String) -> Unit) {
    var url by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val saveFileToScopedStorage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsToGrantedMap: Map<String, Boolean> ->
            if (permissionsToGrantedMap.values.all { it }) {
                Toast.makeText(context, "Permissions success granted", Toast.LENGTH_SHORT).show()
                Log.e("download_screen", "permissions success granted")
                onDownloadClick(url)
            } else {
                //view model denied
                Toast.makeText(context, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    )
    //Состояние сети теперь мониторится иначе
    //SystemBroadcastReceiver(systemAction = , onSystemEvent = )
    NoNetworkMessage(isNetworkAvailable)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Download video file")
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text(text = "Enter url") })
        Button(
            onClick = {
                if (hasPermissions(context).not()) {
                    saveFileToScopedStorage.launch(PERMISSION.toTypedArray())
                } else {
                    onDownloadClick(url)
                }
            },
            enabled = isNetworkAvailable && Patterns.WEB_URL.matcher(url).matches()
        ) {
            Text(text = "Download", color = MaterialTheme.colors.background)
        }
    }
}

@Composable
fun NoNetworkMessage(networkAvailable: Boolean) {
    if (!networkAvailable) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = MaterialTheme.colors.secondaryVariant),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Network is not available", fontSize = 22.sp)
        }
    }
}

