package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AVTickerMonthlySeriesEntity
import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AlphaVantageRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Temp(
    val repository: AlphaVantageRepository,
) {

    @GetMapping("/api/temp")
    fun pim(): AVTickerMonthlySeriesEntity? {

        return repository.findBy(Ticker("IBM"))
    }
}
