package com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory

import com.myrran.stockator.application.TickerHistoryRepository
import com.myrran.stockator.domain.tickerhistory.Ticker
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import com.myrran.stockator.infrastructure.threadpools.ThreadPoolsConfiguration
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Repository
import java.util.concurrent.CompletableFuture

@Repository
class AVTickerHistoryRepository(

    private val client: AVClient,
    private val adapter: AVTickerHistoryAdapter

): TickerHistoryRepository {

    override fun findBy(ticker: Ticker): TickerHistory? =

        client.findBy(ticker)
            ?.let { adapter.toDomain(it) }

    @Async(value = ThreadPoolsConfiguration.ALPHA_VANTAGE_THREAD_POLL)
    override fun findByAsync(ticker: Ticker): CompletableFuture<TickerHistory?> =

        CompletableFuture.completedFuture(findBy(ticker))

}
