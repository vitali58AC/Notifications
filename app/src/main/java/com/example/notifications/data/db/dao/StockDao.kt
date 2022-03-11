package com.example.notifications.data.db.dao

import androidx.room.*
import com.example.notifications.data.db.stocks.Stock
import com.example.notifications.data.db.stocks.StockContract
import com.example.notifications.data.db.users.UserContract
import com.example.notifications.data.db.users.UserWithMessages
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stocks: List<Stock>): List<Long>

    @Query("SELECT * FROM ${StockContract.STOCK_TABLE}")
    fun getStocks(): Flow<List<Stock>>
}