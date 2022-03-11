package com.example.notifications.view_models

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifications.data.Constants
import com.example.notifications.data.db.stocks.Stock
import com.example.notifications.repositories.StockRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StockViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StockRepository(application)

    var firstLaunchState by mutableStateOf(true)
    val stockListState = mutableStateListOf<Stock>()

    fun checkToken() {
        viewModelScope.launch {
            firstLaunchState = repository.getFromPreferences(Constants.TOKEN_NAME).not()
            if (firstLaunchState) {
                try {
                    val token = repository.getTokenSuspend()
                    if (token != null) {
                        repository.saveToPreferences(Constants.TOKEN_NAME, token)
                    }
                    Log.e("stock_view_model", "success token is $token")
                } finally {
                    firstLaunchState = false
                }
            }
        }
    }

    fun getMessagesList() {
        viewModelScope.launch {
            try {
                repository.getStocks().collect { stocks ->
                    stockListState.clear()
                    stockListState.addAll(stocks)
                }
            } catch (t: Throwable) {
                Log.e("chat_view_model", "error with get messages list ${t.message}")
            }
        }
    }
}