package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.IncreaseI
import com.myrran.stockator.domain.misc.IncreaseNaN
import com.myrran.stockator.domain.tickerhistory.MonthHistory
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import com.myrran.stockator.domain.tickerhistory.TickerId
import org.springframework.stereotype.Component
import java.time.Month
import java.util.Locale

@Component
class TickerAnalyzerAdapter {

    fun fromDomain(domain: TickerHistory): TickerMonthlySeriesDTO =
        TickerMonthlySeriesDTO(
            ticker = domain.tickerId.symbol,
            firstDate = domain.firstDate(),
            lastDate = domain.lastDate(),
            yearIncrease = domain.history.yearlyIncrease().mapKeys { it.key.value }.mapValues { it.value.value.roundTo2Decimals() },
            averageIncreasesByMonth = Month.entries.associateWith { domain.averageIncreaseOf(it).value.roundTo2Decimals() },
            medianIncreasesByMonth = Month.entries.associateWith { domain.medianIncreaseOf(it).value.roundTo2Decimals() },
            numberOfNegativeIncreasesByMonth = Month.entries.associateWith { domain.numberOfNegativeIncreasesOn(it) },
            monthlyData = domain.history.monthlyHistory
                .map { fromDomain(it)}
                .groupBy { it.closingDay.year }
                .mapValues { entry -> entry.value.associateBy { it.closingDay.month.name } }
        )

    fun toDomain(tickerSymbols: List<String>?): List<TickerId>? =
        tickerSymbols?.map { TickerId(it) }

    private fun fromDomain(domain: MonthHistory): MonthlyRatesDTO =
        MonthlyRatesDTO(
            openingPrice = domain.openingPrice.amount,
            closingPrice = domain.closingPrice.amount,
            closingDay = domain.closingDay,
            increase = domain.increase.toDouble()?.roundTo2Decimals()
        )

    private fun IncreaseI.toDouble(): Double? =
        when (this) {
            is Increase -> this.value
            is IncreaseNaN -> null
        }

    private fun Double.roundTo2Decimals(): Double =
        "%.2f".format(Locale.UK, this).toDouble()
}


