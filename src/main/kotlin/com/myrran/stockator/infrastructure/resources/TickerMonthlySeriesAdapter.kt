package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.IncreaseI
import com.myrran.stockator.domain.misc.IncreaseNaN
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import org.springframework.stereotype.Component
import java.time.Month

@Component
class TickerMonthlySeriesAdapter {

    fun fromDomain(domain: TickerHistory): TickerMonthlySeriesDTO =
        TickerMonthlySeriesDTO(
            ticker = domain.ticker.symbol,
            firstDate = domain.firstDate(),
            lastDate = domain.lastDate(),
            averageIncreasesByMonth = Month.entries.associateWith { domain.averageIncreaseOf(it).value },
            medianIncreasesByMonth = Month.entries.associateWith { domain.medianIncreaseOf(it).value },
            numberOfNegativeIncreasesByMonth = Month.entries.associateWith { domain.numberOfNegativeIncreasesOn(it) },
            monthlyData = domain.monthlyHistory.monthlyRates.map {
                MonthlyDataDTO(
                    openingPrice = it.openingPrice.amount,
                    closingPrice = it.closingPrice.amount,
                    closingDay = it.closingDay,
                    increase = it.increase.toDouble()
                )
            }
        )

    fun IncreaseI.toDouble(): Double? =
        when (this) {
            is Increase -> this.value
            is IncreaseNaN -> null
        }
}
