package com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries

import com.myrran.stockator.domain.Ticker
import org.springframework.stereotype.Repository

@Repository
open class AlphaVantageRepository(

    private val alphaVantageClient: AlphaVantageClient
) {

    open fun findBy(ticker: Ticker): AVTickerMonthlySeriesEntity? =
        alphaVantageClient.findBy(ticker)
}
