package com.example.notifications.view_models

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifications.data.utils.IncorrectMimeTypeException
import com.example.notifications.repositories.DownloadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DownloadViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DownloadRepository(application)
    private val tag = "download_viewModel"

    var downloadProgress by mutableStateOf(false)

    fun downloadVideo(url: String, progressCallback: (Long, Long) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    downloadProgress = true
                    repository.saveVideo(url) { progress, fileSize ->
                        //Log.e(tag, "progress in viewModel is $progress")
                        progressCallback(progress, fileSize)
                    }
                } catch (t: Throwable) {
                    Log.e(tag, "error with download movie from url ${t.message} exception name $t")
                } finally {
                    downloadProgress = false
                }
            }
        }
    }
}