package com.myrran.stockator.infrastructure.repositories.alphavantageticker

import com.myrran.stockator.domain.ticker.Ticker
import com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory.AVClient
import org.springframework.stereotype.Repository

@Repository
class TickerRepository(

    private val client: AVClient,
    private val adapter: TickerAdapter
) {
    fun findAll(): List<Ticker> =

        client.findAllTickers()
            .map { adapter.toDomain(it) }
}
