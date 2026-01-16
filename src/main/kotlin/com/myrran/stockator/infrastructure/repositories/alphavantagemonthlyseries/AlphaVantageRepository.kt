package com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries

import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.domain.TickerMonthlySeries
import org.springframework.stereotype.Repository

@Repository
class AlphaVantageRepository(

    private val alphaVantageClient: AlphaVantageClient,
    private val adapter: AlphaVantageAdapter
) {
    fun findBy(ticker: Ticker): TickerMonthlySeries? =

        alphaVantageClient.findBy(ticker)
            ?.let { adapter.toDomain(it) }

}
