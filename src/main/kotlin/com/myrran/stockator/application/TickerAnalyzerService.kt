package com.myrran.stockator.application

import com.myrran.stockator.domain.misc.TimeRange
import com.myrran.stockator.domain.rules.RulesForAGoodMonthI
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import com.myrran.stockator.domain.tickerhistory.TickerId
import org.springframework.stereotype.Service
import java.time.Month

@Service
class TickerAnalyzerService(

    val repository: TickerHistoryRepository

) {
    fun getHistory(tickerId: TickerId, historyTimeRange: TimeRange): TickerHistory? =

        repository.findBy(tickerId, historyTimeRange)

    fun goodTickersFor(tickerIds: List<TickerId>, historyTimeRange: TimeRange, month: Month, rules: RulesForAGoodMonthI): List<TickerId> =

        tickerIds
            .map { repository.findByAsync(it, historyTimeRange) }
            .mapNotNull { it.get() }
            .filter { rules.satisfiesTheRules(it, month) }
            .map { it.tickerId }

    fun goodMonthsFor(tickerId: TickerId, historyTimeRange: TimeRange, rules: RulesForAGoodMonthI): List<Month> {

        val history = repository.findBy(tickerId, historyTimeRange) ?: return emptyList()
        return Month.entries.filter { rules.satisfiesTheRules(history, it) }
    }
}
