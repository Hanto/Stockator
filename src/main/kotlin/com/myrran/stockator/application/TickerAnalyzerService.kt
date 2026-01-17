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

    fun getMonthlySeries(ticker: Ticker): TickerMonthlySeries? =

        repository.findBy(ticker)

    fun goodTickersFor(tickers: List<Ticker>, month: Month): List<Ticker> =

        tickers
            .map { repository.findByAsync(it) }
            .mapNotNull { it.get() }
            .filter { hasAGoodMonthOverTheYears(it, month) }
            .map { it.ticker }

    fun goodMonthsFor(ticker: Ticker): List<Month> {

        val ticker = repository.findBy(ticker) ?: return emptyList()
        return Month.entries.filter { hasAGoodMonthOverTheYears(ticker, it) }
    }

    private fun hasAGoodMonthOverTheYears(series: TickerMonthlySeries, month: Month): Boolean {

        val average = series.averageIncreaseOf(month)
        val median = series.medianIncreaseOf(month)
        val badMonths = series.numberOfNegativeIncreasesOn(month)

        return average > Percentage(3.0) && median > (average * 0.70) && badMonths <= 4
    }
}
