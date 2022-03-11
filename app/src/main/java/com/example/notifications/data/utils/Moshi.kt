package com.example.notifications.data.utils

import android.util.Log
import com.example.notifications.data.db.stocks.Stock
import com.example.notifications.data.db.users.UserMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Moshi {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val moshiUserMessageAdapter = moshi.adapter(UserMessage::class.java).nonNull()
    private val moshiStockAdapter = moshi.adapter(Stock::class.java).nonNull()

    fun convertUserMessageJsonToInstance(responseString: String): UserMessage? {
        var message: UserMessage? = null
        try {
            message = moshiUserMessageAdapter.fromJson(responseString)
        } catch (e: Exception) {
            Log.e("moshi", "error with convert message moshi ${e.message}")
        }
        return message
    }

    fun convertStockJsonToInstance(responseString: String): Stock? {
        var stock: Stock? = null
        try {
            stock = moshiStockAdapter.fromJson(responseString)
        } catch (e: Exception) {
            Log.e("moshi", "error with convert stock moshi ${e.message}")
        }
        return stock
    }
}