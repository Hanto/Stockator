package com.myrran.stockator.application

import com.myrran.stockator.domain.Percentage
import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.domain.TickerMonthlySeries
import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AlphaVantageRepository
import org.springframework.stereotype.Service
import java.time.Month

@Service
class TickerAnalyzerService(
    val repository: AlphaVantageRepository
) {

    fun analyzeTickers(tickers: List<Ticker>, month: Month): List<Ticker> =

        tickers.filter { analyzeTicker(it, month) }

    fun analyzeTicker(ticker: Ticker, month: Month): Boolean =

        repository.findBy(ticker)?.let { analyzeSeries(it, month) } ?: false

    private fun analyzeSeries(series: TickerMonthlySeries, month: Month): Boolean {

        val average = series.averageIncreaseOf(month)
        val median = series.medianIncreaseOf(month)
        val badMonths = series.numberOfNegativeIncreasesOn(month)

        return average > Percentage(4.0) && median > (average * 0.70) && badMonths <= 2
    }
}
