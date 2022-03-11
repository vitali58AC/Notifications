package com.example.notifications.data.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

val PERMISSION = listOfNotNull(
    android.Manifest.permission.READ_EXTERNAL_STORAGE,
    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        .takeIf { haveQ().not() }
)

fun hasPermissions(context: Context) = PERMISSION.all {
    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}

fun haveQ() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

