package com.myrran.stockator.application

import com.myrran.stockator.domain.misc.TimeRange
import com.myrran.stockator.domain.rules.TickerHistoryRules
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import com.myrran.stockator.domain.tickerhistory.TickerId
import com.myrran.stockator.infrastructure.repositories.alphavantageticker.TickerRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Month

@Service
class TickerAnalyzerService(

    val historyRepository: TickerHistoryRepository,
    val tickerRepository: TickerRepository,

) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun getHistory(tickerId: TickerId, historyTimeRange: TimeRange): TickerHistory? =

        historyRepository.findBy(tickerId, historyTimeRange)

    fun goodTickersFor(tickerIds: List<TickerId>?, historyTimeRange: TimeRange, rules: TickerHistoryRules, month: Month): List<TickerId> =

        tickerIds ?: tickerRepository.findAll().map { it.tickerId }
            .map { historyRepository.findByAsync(it, historyTimeRange) }
            .mapNotNull { it.get() }
            .also { log.info("Looking into ${it.size}") }
            .filter { it.satisfiesRulesThatMonth(rules, month) }
            .also { log.info("${it.size} tickers found") }
            .map { it.tickerId }

    fun goodTickersFor(tickerIds: List<TickerId>?, historyTimeRange: TimeRange, rules: TickerHistoryRules): List<TickerId> =

        tickerIds ?: tickerRepository.findAll().map { it.tickerId }
            .map { historyRepository.findByAsync(it, historyTimeRange) }
            .mapNotNull { it.get() }
            .also { log.info("Looking into ${it.size}") }
            .filter { it.satisfiesRulesAnyMonth(rules) }
            .also { log.info("${it.size} tickers found") }
            .map { it.tickerId }

    fun goodMonthsFor(tickerId: TickerId, historyTimeRange: TimeRange, rules: TickerHistoryRules): List<Month> {

        val history = historyRepository.findBy(tickerId, historyTimeRange) ?: return emptyList()
        return Month.entries.filter { rules.satisfiesTheRules(history, it) }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun List<TickerHistory>.toSymbols(): List<String> =
        this.map { it.tickerId.symbol }
}
