package com.example.notifications.repositories

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.notifications.data.db.Database
import com.example.notifications.data.db.stocks.Stock
import com.example.notifications.data.db.users.UserMessage
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class StockRepository(context: Context) {

    private var sharedPrefers: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val stockDao = Database.instance.stockDao()


    fun getFromPreferences(key: String): Boolean {
        val result = sharedPrefers.getString(key, null)
        Log.e("stock_repo", "old token is $result")
        return result != null
    }

    fun saveToPreferences(key: String, value: String) {
        sharedPrefers
            .edit()
            .putString(key, value)
            .apply()
    }


    suspend fun getTokenSuspend(): String? = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                continuation.resume(token)
            }
            .addOnFailureListener { exception ->
                Log.e("stock_screen", "error with get token $exception")
                continuation.resume(null)
            }
            .addOnCanceledListener { continuation.resume(null) }
    }

    suspend fun saveStock(stock: Stock): List<Long> {
        return stockDao.insertStock(listOf(stock))
    }

    fun getStocks() = stockDao.getStocks()


    companion object {
        const val SHARED_PREFS_NAME = "notifications_prefs"
    }
}