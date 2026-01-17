package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.application.TickerAnalyzerService
import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Percentage
import com.myrran.stockator.domain.rules.RulesForAGoodMonth
import com.myrran.stockator.domain.tickerhistory.Ticker
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Month

@RestController
class TickerAnalyzerResource(
    val service: TickerAnalyzerService,
    val adapter: TickerMonthlySeriesAdapter,
    val properties: TickerAnalyzerProperties
) {

    @GetMapping("api/monthlySeries/{tickerSymbol}")
    fun getMonthlySeries(
        @PathVariable tickerSymbol: String
    ): TickerMonthlySeriesDTO? {

        val ticker = Ticker(tickerSymbol)

        return service.getMonthlySeries(ticker)
            ?.let { adapter.fromDomain(it) }
    }

    @GetMapping("/api/goodMonthsFor/{tickerSymbol}")
    fun analyze(
        @PathVariable tickerSymbol: String,
    ): List<String> {

        val ticker = Ticker(tickerSymbol)
        val rules = RulesForAGoodMonth(
            minimumAverageIncrease = Increase(properties.goodMonthMinimumAverageIncrease),
            minimumMedianComparedToAverage = Percentage(properties.goodMonthMimumMedianComparedToAverage),
            maximumNumberOfNegativeIncreases = properties.goodMonthMaximumNumberOfNegativeIncreases
        )

        return service.goodMonthsFor(ticker, rules).map { it.name }
    }

    @GetMapping("/api/goodTickersFor/{month}")
    fun analyze(
        @PathVariable month: Month,
        @RequestParam tickerSymbols: List<String>?
    ): List<TickerDTO> {

        val tickers = (tickerSymbols ?: properties.defaultTickers).map { Ticker(it) }
        val rules = RulesForAGoodMonth(
            minimumAverageIncrease = Increase(properties.goodMonthMinimumAverageIncrease),
            minimumMedianComparedToAverage = Percentage(properties.goodMonthMimumMedianComparedToAverage),
            maximumNumberOfNegativeIncreases = properties.goodMonthMaximumNumberOfNegativeIncreases
        )

        return service.goodTickersFor(tickers, month, rules)
            .map { TickerDTO(it.symbol) }
    }
}
