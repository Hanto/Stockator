package com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries

import com.myrran.stockator.domain.Ticker
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

@Repository
open class AlphaVantageRepository(

    private val alphaVantageClient: AlphaVantageClient
) {

    @Cacheable(cacheManager = "mapDBCacheManager", cacheNames = ["tickerMonthlySeries"], key = "#ticker.symbol")
    open fun findBy(ticker: Ticker): AVTickerMonthlySeriesEntity? =
        alphaVantageClient.findBy(ticker)
}
