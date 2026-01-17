package com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries

import com.myrran.stockator.domain.tickerseries.Ticker
import com.myrran.stockator.domain.tickerseries.TickerMonthlySeries
import com.myrran.stockator.infrastructure.threadpools.ThreadPoolsConfiguration
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Repository
import java.util.concurrent.CompletableFuture

@Repository
class AlphaVantageRepository(

    private val alphaVantageClient: AlphaVantageClient,
    private val adapter: AlphaVantageAdapter
) {
    fun findBy(ticker: Ticker): TickerMonthlySeries? =

        alphaVantageClient.findBy(ticker)
            ?.let { adapter.toDomain(it) }

    @Async(value = ThreadPoolsConfiguration.ALPHA_VANTAGE_THREAD_POLL)
    fun findByAsync(ticker: Ticker): CompletableFuture<TickerMonthlySeries?> =

        CompletableFuture.completedFuture(findBy(ticker))

}
