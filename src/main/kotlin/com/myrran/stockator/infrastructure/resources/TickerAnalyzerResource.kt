package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.application.TickerAnalyzerService
import com.myrran.stockator.domain.tickerhistory.TickerId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Month

@RestController
class TickerAnalyzerResource(
    val service: TickerAnalyzerService,
    val adapter: TickerAnalyzerAdapter,
    val properties: TickerAnalyzerProperties
) {

    @GetMapping("api/monthlySeries/{tickerSymbol}")
    fun getMonthlySeries(
        @PathVariable tickerSymbol: String
    ): TickerMonthlySeriesDTO? {

        val tickerId = TickerId(tickerSymbol)
        val timeRange = properties.defaultTimeRange()

        return service.getHistory(tickerId, timeRange)
            ?.let { adapter.fromDomain(it) }
    }

    @GetMapping("/api/goodMonthsFor/{tickerSymbol}")
    fun analyze(
        @PathVariable tickerSymbol: String,
    ): List<String> {

        val tickerId = TickerId(tickerSymbol)
        val timeRange = properties.defaultTimeRange()
        val rules = properties.defaultRulesForAGoodMonth()

        return service.goodMonthsFor(tickerId, timeRange, rules).map { it.name }
    }

    @GetMapping("/api/goodTickersFor/{month}")
    fun analyze(
        @PathVariable month: Month,
        @RequestParam tickerSymbols: List<String>?
    ): List<TickerDTO> {

        val tickers = adapter.toDomain(tickerSymbols) ?: properties.defaultTickers()
        val timeRange = properties.defaultTimeRange()
        val rules = properties.defaultRulesForAGoodMonth()

        return service.goodTickersFor(tickers, timeRange, month, rules)
            .map { TickerDTO(it.symbol) }
    }
}
