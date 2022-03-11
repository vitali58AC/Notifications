package com.example.notifications.data.db.stocks

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = StockContract.STOCK_TABLE)
data class Stock(
    @PrimaryKey(autoGenerate = true)
    val stockId: Long,
    val title: String,
    val description: String,
    val imageUrl: String?
)
