package com.myrran.stockator.infrastructure.repositories.alphavantageticker

import com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory.AVClient
import org.springframework.stereotype.Repository

@Repository
class TickerStatusRepository(

    private val client: AVClient
) {
    fun findAll() {

        client.findAllTickers()
    }
}
