package com.example.notifications.repositories

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.notifications.data.utils.Networking
import com.example.notifications.data.utils.haveQ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import java.io.OutputStream
import java.time.Instant


class DownloadRepository(private val context: Context) {

    private val tag = "download_repository"
//https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mov-file.mov

    @SuppressLint("InlinedApi")
    suspend fun saveVideo(url: String, progressCallback: (Long, Long) -> Unit) {
        withContext(Dispatchers.IO) {
            val fileType = getMimeType(url) ?: ""
            val name = "video" + Instant.now()
            Log.e(tag, "File type is $fileType file name is $name")
            val movieUri = saveMovieDetails(name, fileType)
            try {
                downloadMovie(url, movieUri) { progress, fileSize ->
                    progressCallback(progress, fileSize)
                }
                makeMovieVisibleOrNot(movieUri)
            } catch (t: Throwable) {
                Log.e(tag, "Error with video download ${t.message}")
            }
        }
    }

    fun getMimeType(url: String?): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    @SuppressLint("InlinedApi")
    private fun makeMovieVisibleOrNot(movieUri: Uri, value: Int = 0) {
        if (haveQ().not()) return
        val movieDetails = ContentValues().apply {
            put(MediaStore.Video.Media.IS_PENDING, value)
        }
        context.contentResolver.update(movieUri, movieDetails, null, null)
    }

    @SuppressLint("InlinedApi")
    private fun saveMovieDetails(name: String, mimeType: String): Uri {
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }
        val movieCollectionUri = MediaStore.Video.Media.getContentUri(volume)
        val movieDetails = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.Video.Media.MIME_TYPE, mimeType)
            //For get another folder
            put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES)
            //To hide this file about another apps
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }
        return context.contentResolver.insert(movieCollectionUri, movieDetails)!!
    }

    private suspend fun downloadMovie(
        url: String,
        uri: Uri,
        progressCallback: (Long, Long) -> Unit
    ) {
        context.contentResolver.openOutputStream(uri)?.use { outputStream: OutputStream ->
            Log.e("movie_repository", "uri $uri for download file")
            val fileSize = Networking.api.getFile(url).bytes().size.toLong()
            val response = Networking.api.getFileCal(url).awaitResponse()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                body.byteStream().use { inputStream ->
                    val data = ByteArray(8192)
                    var read: Int
                    var progress = 0L
                    //val fileSize = body.contentLength()
                    while (inputStream.read(data).also { read = it } != -1) {
                        outputStream.write(data, 0, read)
                        progress += read
                        Log.e(tag, "progress $progress from $fileSize")
                        progressCallback(progress, fileSize)
                    }
                    progressCallback(progress, fileSize)
                }
            }
            /*Networking.api.getFile(url).byteStream().use { inputStream ->
                Log.e(tag, "input stream size ${inputStream.readBytes().size}")
                inputStream.copyTo(outputStream)
            }*/
        }
    }

    //Sample for ktor
    /*suspend fun HttpClient.downloadFile(file: File, url: String): Flow<DownloadStatus> {
        return flow {
            val response = call {
                url(url)
                method = HttpMethod.Get
            }.response
            val byteArray = ByteArray(response.contentLength()!!.toInt())
            var offset = 0
            do {
                val currentRead = response.content.readAvailable(byteArray, offset, byteArray.size)
                offset += currentRead
                val progress = (offset * 100f / byteArray.size).roundToInt()
                emit(DownloadStatus.Progress(progress))
            } while (currentRead > 0)
            response.close()
            if (response.status.isSuccess()) {
                file.writeBytes(byteArray)
                emit(DownloadStatus.Success)
            } else {
                emit(DownloadStatus.Error("File not downloaded"))
            }
        }
    }
*/
}