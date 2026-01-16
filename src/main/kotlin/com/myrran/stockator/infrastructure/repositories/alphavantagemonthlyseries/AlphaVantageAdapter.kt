package com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries

import com.myrran.stockator.domain.Money
import com.myrran.stockator.domain.MonthlyData
import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.domain.TickerMonthlySeries
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class AlphaVantageAdapter {

    fun from(entity: AVTickerMonthlySeriesEntity): TickerMonthlySeries =

        TickerMonthlySeries(
            ticker = Ticker(entity.metadata.symbol),
            months = entity.monthlyTimeSeries.entries.associate { toDate(it.key) to from(it.value) }
        )

    private fun toDate(dateString: String): LocalDate =
        LocalDate.parse(dateString)

    private fun from(entity: AVMonthlyDataEntity): MonthlyData =

        MonthlyData(price = Money(entity.close.toFloat()))
}
