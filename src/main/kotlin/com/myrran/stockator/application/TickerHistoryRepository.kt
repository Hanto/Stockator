package com.myrran.stockator.application

import com.myrran.stockator.domain.tickerhistory.Ticker
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import java.util.concurrent.CompletableFuture

interface TickerHistoryRepository {

    fun findBy(ticker: Ticker): TickerHistory?
    fun findByAsync(ticker: Ticker): CompletableFuture<TickerHistory?>
}
