package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.application.TickerAnalyzerService
import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.domain.TickerMonthlySeries
import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AlphaVantageRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Month

@RestController
class TickerMonthlySeriesResource(
    val repository: AlphaVantageRepository,
    val service: TickerAnalyzerService,
    val properties: TickerProperties
) {

    @GetMapping("/api/analyze/{ticker}/{month}")
    fun analyze(
        @PathVariable ticker: String,
        @PathVariable month: Month
    ): TickerMonthlySeries? {

        val tickerDomain = Ticker(ticker)
        val result = repository.findBy(tickerDomain)
        val buyStock = service.analyzeTicker(tickerDomain, month)

        println(result?.firstDate())
        println(result?.lastDate())

        println(result?.byMonth[Month.JANUARY]?.map { it.increase })

        println(result?.averageIncreaseOf(Month.JANUARY))
        println(result?.medianIncreaseOf(Month.JANUARY))

        println("BUY STOCK IN ${month}: $buyStock")

        return result
    }

    @GetMapping("/api/analyze/{month}")
    fun analyze(
        @PathVariable month: Month,
        @RequestParam tickers: List<String>?
    ): List<TickerDTO> {

        val tickersDomain = (tickers ?: properties.defaultTickers).map { Ticker(it) }

        return service.analyzeTickers(tickersDomain, month)
            .map { TickerDTO(it.symbol) }
    }
}
