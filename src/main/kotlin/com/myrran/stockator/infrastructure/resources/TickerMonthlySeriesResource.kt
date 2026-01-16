package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.domain.TickerMonthlySeries
import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AlphaVantageRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TickerMonthlySeriesResource(
    val repository: AlphaVantageRepository,
) {

    @GetMapping("/api/temp")
    fun pim(): TickerMonthlySeries? {

        return repository.findBy(Ticker("IBM"))
    }
}
