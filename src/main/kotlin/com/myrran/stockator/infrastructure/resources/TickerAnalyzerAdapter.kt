package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.IncreaseI
import com.myrran.stockator.domain.misc.IncreaseNaN
import com.myrran.stockator.domain.tickerhistory.MonthlyRates
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import com.myrran.stockator.domain.tickerhistory.TickerId
import org.springframework.stereotype.Component
import java.time.Month

@Component
class TickerAnalyzerAdapter {

    fun fromDomain(domain: TickerHistory): TickerMonthlySeriesDTO =
        TickerMonthlySeriesDTO(
            ticker = domain.tickerId.symbol,
            firstDate = domain.firstDate(),
            lastDate = domain.lastDate(),
            averageIncreasesByMonth = Month.entries.associateWith { domain.averageIncreaseOf(it).value },
            medianIncreasesByMonth = Month.entries.associateWith { domain.medianIncreaseOf(it).value },
            numberOfNegativeIncreasesByMonth = Month.entries.associateWith { domain.numberOfNegativeIncreasesOn(it) },
            monthlyData = domain.monthlyHistory.monthlyRates
                .map { fromDomain(it)}
                .groupBy { it.closingDay.year }
                .mapValues { entry -> entry.value.associateBy { it.closingDay.month.name } }
        )

    fun toDomain(tickerSymbols: List<String>?): List<TickerId>? =
        tickerSymbols?.map { TickerId(it) }

    private fun fromDomain(domain: MonthlyRates): MonthlyRatesDTO =
        MonthlyRatesDTO(
            openingPrice = domain.openingPrice.amount,
            closingPrice = domain.closingPrice.amount,
            closingDay = domain.closingDay,
            increase = domain.increase.toDouble()
        )

    private fun IncreaseI.toDouble(): Double? =
        when (this) {
            is Increase -> this.value
            is IncreaseNaN -> null
        }
}
