package com.myrran.stockator.application

import com.myrran.stockator.domain.misc.TimeRange
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import com.myrran.stockator.domain.tickerhistory.TickerId
import java.util.concurrent.CompletableFuture

interface TickerHistoryRepository {

    fun findBy(tickerId: TickerId, timeRange: TimeRange): TickerHistory?
    fun findByAsync(tickerId: TickerId, timeRange: TimeRange): CompletableFuture<TickerHistory?>
}
