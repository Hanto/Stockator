package com.myrran.stockator.application

import com.myrran.stockator.domain.rules.RulesForAGoodMonthI
import com.myrran.stockator.domain.tickerhistory.Ticker
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import org.springframework.stereotype.Service
import java.time.Month

@Service
class TickerAnalyzerService(

    val repository: TickerHistoryRepository

) {
    fun getMonthlySeries(ticker: Ticker): TickerHistory? =

        repository.findBy(ticker)

    fun goodTickersFor(tickers: List<Ticker>, month: Month, rules: RulesForAGoodMonthI): List<Ticker> =

        tickers
            .map { repository.findByAsync(it) }
            .mapNotNull { it.get() }
            .filter { rules.satisfiesTheRules(it, month) }
            .map { it.ticker }

    fun goodMonthsFor(ticker: Ticker, rules: RulesForAGoodMonthI): List<Month> {

        val series = repository.findBy(ticker) ?: return emptyList()
        return Month.entries.filter { rules.satisfiesTheRules(series, it) }
    }
}
