package com.example.notifications.data.utils


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface Api {

    @Streaming
    @GET
    suspend fun getFile(@Url url: String): ResponseBody

    @Streaming
    @GET
    fun getFileCal(@Url url: String): Call<ResponseBody>
}