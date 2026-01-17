package com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory

import com.myrran.stockator.application.TickerHistoryRepository
import com.myrran.stockator.domain.misc.TimeRange
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import com.myrran.stockator.domain.tickerhistory.TickerId
import com.myrran.stockator.infrastructure.threadpools.ThreadPoolsConfiguration
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Repository
import java.util.concurrent.CompletableFuture

@Repository
class AVTickerHistoryRepository(

    private val client: AVClient,
    private val adapter: AVTickerHistoryAdapter

): TickerHistoryRepository {

    override fun findBy(tickerId: TickerId, timeRange: TimeRange): TickerHistory? =

        client.findBy(tickerId)
            ?.let { adapter.toDomain(it, timeRange) }

    @Async(value = ThreadPoolsConfiguration.ALPHA_VANTAGE_THREAD_POLL)
    override fun findByAsync(tickerId: TickerId, timeRange: TimeRange): CompletableFuture<TickerHistory?> =

        CompletableFuture.completedFuture(findBy(tickerId, timeRange))
}
