package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.application.TickerAnalyzerService
import com.myrran.stockator.domain.Ticker
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Month

@RestController
class TickerAnalyzerResource(
    val service: TickerAnalyzerService,
    val adapter: TickerMonthlySeriesAdapter,
    val properties: TickerProperties
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
    ): List<Month> {

        val ticker = Ticker(tickerSymbol)

        return service.goodMonthsFor(ticker)
    }

    @GetMapping("/api/goodTickersFor/{month}")
    fun analyze(
        @PathVariable month: Month,
        @RequestParam tickerSymbols: List<String>?
    ): List<TickerDTO> {

        val tickers = (tickerSymbols ?: properties.defaultTickers).map { Ticker(it) }

        return service.goodTickersFor(tickers, month)
            .map { TickerDTO(it.symbol) }
    }
}
