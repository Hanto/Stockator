package com.myrran.stockator.infrastructure.repositories.alphavantageticker

data class TickerStatusEntity(

    val symbol: String,
    val name: String,
    val exchange: String,
    val assetType: String,
    val ipoDate: String,
    val delistingDate: String?,
    val status: String
)
